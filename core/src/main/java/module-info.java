import se.iths.groupmembers.spi.Page;

module core {
    requires spi;
    requires org.apache.commons.io;
    requires dao;
    requires com.google.gson;
    uses Page;
}