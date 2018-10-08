package by.epam.edu.webdriver.data;

/**
 * Created by Aliaksandr_Sheliutsi on 1/27/2017.
 */
public enum Folder {
    Inbox,
    Sent,
    Drafts;

    public String getValue() {
        switch (this) {
            case Inbox:
                return "inbox";
            case Sent:
                return "sent";
            case Drafts:
                return "drafts";
            default:
                return "inbox";
        }
    }

}
