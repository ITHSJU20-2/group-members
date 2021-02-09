import se.iths.groupmembers.router.get.css.MainCSS;
import se.iths.groupmembers.router.get.css.NormalizeCSS;
import se.iths.groupmembers.router.get.html.Error;
import se.iths.groupmembers.router.get.html.Index;
import se.iths.groupmembers.router.get.html.Person;
import se.iths.groupmembers.router.get.img.CatIMG;
import se.iths.groupmembers.router.get.js.PersonJS;
import se.iths.groupmembers.router.get.pdf.Laboration1PDF;
import se.iths.groupmembers.spi.Page;

module router {
    requires spi;
    provides Page with Index, Error, Person, NormalizeCSS, MainCSS, CatIMG, Laboration1PDF, PersonJS;
}