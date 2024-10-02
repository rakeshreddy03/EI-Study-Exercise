// Product
interface Document {
    void open();
}

// Concrete products
class PDFDocument implements Document {
    @Override
    public void open() {
        System.out.println("Opening PDF document.");
    }
}

class WordDocument implements Document {
    @Override
    public void open() {
        System.out.println("Opening Word document.");
    }
}

// Factory
class DocumentFactory {
    public static Document createDocument(String type) {
        if (type.equalsIgnoreCase("pdf")) {
            return new PDFDocument();
        } else if (type.equalsIgnoreCase("word")) {
            return new WordDocument();
        }
        return null;
    }
}

public class FactoryPatternDemo {
    public static void main(String[] args) {
        Document pdf = DocumentFactory.createDocument("pdf");
        pdf.open();

        Document word = DocumentFactory.createDocument("word");
        word.open();
    }
}
