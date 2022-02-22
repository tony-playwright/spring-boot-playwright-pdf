package spring.boot.playwright.pdf.controller;

import  spring.boot.playwright.pdf.response.*;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.Media;
import com.microsoft.playwright.options.WaitUntilState;
import io.swagger.annotations.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.WebServiceException;
import java.io.File;
import java.nio.file.Path;

@RestController
@RequestMapping("/app")
public class ApplicationController {

    @ApiOperation(value = "create pdf from html by playwright", code = 200, consumes = "text/html; charset=UTF-8", produces = "application/pdf", notes = "return pdf ")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "pdf has been created", response = File.class),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "No authorize to call service", response = CommonResponse.class),
            @ApiResponse(code = 400, message = "bad request", response = CommonResponse.class),
            @ApiResponse(code = 403, message = "authorize to call service", response = CommonResponse.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Bearer", paramType = "header")})
    @PostMapping(path = "/html2pdf", consumes = "text/html; charset=UTF-8", produces = "application/pdf")
    public ResponseEntity<byte[]> generatePdfByPlaywright(@RequestBody String payload) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename = "ClientPacket" + Long.toString(System.nanoTime()) + ".pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        byte[] contents = this.convertHtmlToPdf(payload);
        ResponseEntity<byte[]> filenamepdf = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
        return filenamepdf;
    }

    public byte[] convertHtmlToPdf(String content) {
        byte[] pdfContent = null;
        try {
            Playwright playwright = Playwright.create();
            BrowserType chromium = playwright.chromium();

            Browser browser = chromium.launch();
            BrowserContext context = browser.newContext(new Browser.NewContextOptions().setDeviceScaleFactor(2));
            Page page = context.newPage();

            page.emulateMedia(new Page.EmulateMediaOptions().setMedia(Media.SCREEN));
            Page.PdfOptions pagePdfOptions = new Page.PdfOptions();
            pagePdfOptions.setPrintBackground(true);
            pagePdfOptions.setFormat("A4");
            page.setContent(content, new Page.SetContentOptions().setWaitUntil(WaitUntilState.NETWORKIDLE));
            pdfContent = page.pdf(pagePdfOptions);
            browser.close();
            playwright.close();
        } catch (Exception e) {
           e.printStackTrace();
            throw new WebServiceException(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e);
        }

        return pdfContent;
    }
}