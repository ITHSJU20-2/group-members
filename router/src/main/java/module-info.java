import se.iths.groupmembers.Index;
import se.iths.groupmembers.spi.Page;

module router {
    requires spi;
    requires rt;
    provides Page with Index;
}