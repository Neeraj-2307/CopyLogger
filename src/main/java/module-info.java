module com.neeraj.copylogger {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.dustinredmond.fxtrayicon;
    requires java.desktop;


    opens com.neeraj.copylogger to javafx.fxml;
    exports com.neeraj.copylogger;
}