import se.iths.groupmembers.router.Error;
import se.iths.groupmembers.router.GlobalCSS;
import se.iths.groupmembers.router.Index;
import se.iths.groupmembers.router.NormalizeCSS;
import se.iths.groupmembers.spi.Page;

module router {
    requires spi;
    provides Page with Index, Error, NormalizeCSS, GlobalCSS;
}