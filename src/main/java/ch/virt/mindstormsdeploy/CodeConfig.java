package ch.virt.mindstormsdeploy;

/**
 * @author VirtCode
 * @version 1.0
 */
public class CodeConfig {
    private String[] codeFileEndings = {".ts"};
    private String sourceFolder = "src";
    private String priorityPrefix = "//priority:";

    public String[] getCodeFileEndings() {
        return codeFileEndings;
    }

    public String getSourceFolder() {
        return sourceFolder;
    }

    public String getPriorityPrefix() {
        return priorityPrefix;
    }
}
