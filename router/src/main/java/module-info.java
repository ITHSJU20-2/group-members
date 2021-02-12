import se.iths.groupmembers.router.get.css.MainCSS;
import se.iths.groupmembers.router.get.html.Error;
import se.iths.groupmembers.router.get.html.GetUsers;
import se.iths.groupmembers.router.get.html.Index;
import se.iths.groupmembers.router.get.html.Person;
import se.iths.groupmembers.router.get.img.CatIMG;
import se.iths.groupmembers.router.get.js.MainJS;
import se.iths.groupmembers.router.get.js.PersonJS;
import se.iths.groupmembers.router.get.pdf.Laboration1PDF;
import se.iths.groupmembers.router.post.*;
import se.iths.groupmembers.spi.Page;

module router {
    requires spi;
    requires dao;
    requires com.google.gson;
    provides Page with Index, Error, Person, MainCSS, CatIMG, Laboration1PDF, PersonJS, GetUsers,
            AddUser, DeleteUserByFirstName, DeleteUserByLastName, DeleteUserById, MainJS, GetUserByFirstName,
            GetUserById, UpdateUser, GetByFirstLast;
}