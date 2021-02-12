import se.iths.groupmembers.spi.Page;

module core {
    requires spi;
    requires dao;
    requires com.google.gson;
    uses Page;
}