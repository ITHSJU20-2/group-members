
module se.iths.db {
    requires java.persistence;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires net.bytebuddy;
    requires com.fasterxml.classmate;
    requires java.xml.bind;
    opens se.iths.db to org.hibernate.orm.core;
    exports se.iths.db;
}