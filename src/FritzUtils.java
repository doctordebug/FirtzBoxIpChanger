import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.*;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.soap.*;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.DatagramSocket;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static com.oracle.webservices.internal.api.EnvelopeStyle.Style.XML;

/**
 * Created by olisa_000 on 05.05.17.
 */
public class FritzUtils {

    static final String body =
            "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "        <s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\" s:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                    "        <s:Body>\n" +
                    "        <u:ForceTerminationResponse xmlns:u=\"urn:schemas-upnp-org:service:WANIPConnection:1\"></u:ForceTerminationResponse>\n" +
                    "        </s:Body>\n" +
                    "        </s:Envelope>";

    static final String body2 ="<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
            "<s:Envelope s:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "  <s:Body>\n" +
            "    <u:GetExternalIPAddress xmlns:u=\"urn:schemas-upnp-org:service:WANIPConnection:1\" />\n" +
            "  </s:Body>\n" +
            "</s:Envelope>";

    static final String head = "" +
            "POST /igdupnp/control/WANIPConn1 HTTP/1.1'\n" +
            "Host: fritz.box:49000\n" +
            "SoapAction: urn:schemas-upnp-org:service:WANIPConnection:1#ForceTermination\n" +
            "Content-Type: text/xml; charset=\"utf-8\"\n" +
            "Content-Length: " + body.length();

    public static String reconnect() {
        try {
            URL sharepoint = new URL("http://fritz.box:49000/igdupnp/control/WANIPConn1");
            URLConnection sharepoint_connection = sharepoint.openConnection();

            sharepoint_connection.setRequestProperty("Man", "/igdupnp/control/WANIPConn1 HTTP/1.1");
            sharepoint_connection.setRequestProperty("Host", "fritz.box:49000");
            sharepoint_connection.setRequestProperty("Content-Type", "text/xml; charset=\"utf-8\"");
            sharepoint_connection.setRequestProperty("Content-Length", Integer.toString(body.length()));
            sharepoint_connection.setRequestProperty("SoapAction", "urn:schemas-upnp-org:service:WANIPConnection:1#ForceTermination");

            // Write request body to SharePoint
            sharepoint_connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(sharepoint_connection.getOutputStream());
            writer.write(body);
            writer.close();

            // Read result from SharePoint
            BufferedReader reader = new BufferedReader(new InputStreamReader(sharepoint_connection.getInputStream()));
            String inputLine;
            StringBuffer sb = new StringBuffer();
            while ((inputLine = reader.readLine()) != null)
                sb.append(inputLine);
            reader.close();

            return sb.toString();
        } catch (Exception e) {
            return "Request failed \n" + e.toString();
        }
    }

    public static String getIp(JTextField f) {
        try {
            URL sharepoint = new URL("http://fritz.box:49000/igdupnp/control/WANIPConn1");
            URLConnection sharepoint_connection = sharepoint.openConnection();

            sharepoint_connection.setRequestProperty("Man", "/igdupnp/control/WANIPConn1 HTTP/1.1");
            sharepoint_connection.setRequestProperty("Host", "fritz.box:49000");
            sharepoint_connection.setRequestProperty("Content-Type", "text/xml; charset=\"utf-8\"");
            sharepoint_connection.setRequestProperty("Content-Length", Integer.toString(body2.length()));
            sharepoint_connection.setRequestProperty("SoapAction", "urn:schemas-upnp-org:service:WANIPConnection:1#GetExternalIPAddress");

            // Write request body to SharePoint
            sharepoint_connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(sharepoint_connection.getOutputStream());
            writer.write(body2);
            writer.close();

            // Read result from SharePoint
            BufferedReader reader = new BufferedReader(new InputStreamReader(sharepoint_connection.getInputStream()));
            String inputLine;
            StringBuffer sb = new StringBuffer();
            while ((inputLine = reader.readLine()) != null)
                sb.append(inputLine);
            reader.close();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(sb.toString()));
            Document doc = builder.parse(is);
            String node = doc.getElementsByTagName("NewExternalIPAddress").item(0).getTextContent();
            f.setText(node);return sb.toString();
        } catch (Exception e) {
            return "Request failed \n" + e.toString();
        }
    }

}

