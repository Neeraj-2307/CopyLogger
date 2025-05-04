module com.neeraj.copylogger {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.dustinredmond.fxtrayicon;


    opens com.neeraj.copylogger to javafx.fxml;
    exports com.neeraj.copylogger;
}