interface FileProcessor{
    String  view();
}


class BasicFileProcessor implements FileProcessor{
    @Override
    public String view() {
        return "This is basic file \n";
    }
}

interface FileProcessorDecorator extends FileProcessor{
    String view();
}
class EncryptionDecorator implements FileProcessorDecorator{
    FileProcessor processor;
    public EncryptionDecorator(FileProcessor processor){
        this.processor = processor;
    }
    @Override
    public String view() {
        return processor.view() + " File is encrypted. \n";
    }
}

class DecryptionDecorator implements FileProcessorDecorator{
    FileProcessor processor;
    public DecryptionDecorator(FileProcessor processor){
        this.processor = processor;
    }
    @Override
    public String view() {
        return processor.view() + " File is decrypted. \n";
    }
}

class CompressionDecorator implements FileProcessorDecorator{
    FileProcessor processor;
    public CompressionDecorator(FileProcessor processor){
        this.processor = processor;
    }
    @Override
    public String view() {
        return processor.view() + " File is compressed. \n";
    }
}

class ExtractionDecorator implements FileProcessorDecorator{
    FileProcessor processor;
    public ExtractionDecorator(FileProcessor processor){
        this.processor = processor;
    }
    @Override
    public String view() {
        return processor.view() + " File is Extracted. \n";
    }
}

class PasswordProtectionDecorator implements FileProcessorDecorator{
    FileProcessor processor;
    public PasswordProtectionDecorator(FileProcessor processor){
        this.processor = processor;
    }
    @Override
    public String view() {
        return processor.view() + " File is password protected. \n";
    }
}

class UnlockDecorator implements FileProcessorDecorator{
    FileProcessor processor;
    public UnlockDecorator(FileProcessor processor){
        this.processor = processor;
    }
    @Override
    public String view() {
        return processor.view() + " File is unlocked. \n";
    }
}

class Main{
    public static void main(String[] args) {
        FileProcessor processor = new BasicFileProcessor();
        System.out.println(processor.view());


        //Now decorating
        System.out.println("Decoration started:");

         processor = new PasswordProtectionDecorator(processor);
         processor = new CompressionDecorator(processor);

        System.out.println(processor.view());
    }
}