import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;

import static java.lang.Integer.parseInt;

public class ProcessReader {
    private String filename;
    ProcessReader(String filename){
        this.filename = filename;
    }
    public ProcessList read() throws Exception{
        ProcessList pl = new ProcessList();
        File file = new File(filename);
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
        doc.getDocumentElement().normalize();

        NodeList processNodes = doc.getElementsByTagName("process");

        for (int i = 0; i < processNodes.getLength(); i++) {
            Element process = (Element) processNodes.item(i);

            int pid = parseInt(process.getElementsByTagName("pid").item(0).getTextContent());
            int arrivaltime = parseInt(process.getElementsByTagName("arrivaltime").item(0).getTextContent());
            int servicetime = parseInt(process.getElementsByTagName("servicetime").item(0).getTextContent());
            Process p = new Process(pid, arrivaltime, servicetime);
            pl.addProcess(p);
        }
        return pl;
    }
}

