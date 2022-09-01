package cn.ixinjiu.controller;

import cn.ixinjiu.entity.Book;
import cn.ixinjiu.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by XinChen on 2022-08-30
 *
 * @Description:TODO
 */
@RestController
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping(value = "/findAll")
    public void findAll() {
        PageRequest pageRequest = PageRequest.of(2, 3);
        Page<Book> page = bookService.getBookByPage(pageRequest);
        System.out.println("总页数:" + page.getTotalPages());
        System.out.println("总记录数:" + page.getTotalElements());
        System.out.println("查询结果:" + page.getContent());
        //从0开始记，所以加上1
        System.out.println("当前页数:" + (page.getNumber() + 1));
        System.out.println("当前记录数:" + page.getNumberOfElements());
        System.out.println("每页记录数:" + page.getSize());
        /**
         * >>> result:
         * 总页数:3
         * 总记录数:8
         * 查询结果:[Book(id=7, name=g, author=ggg, price=18.0, description=null), Book(id=8, name=h, author=hhh, price=19.0, description=null)]
         * 当前页数:3
         * 当前记录数:2
         * 每页记录数:3
         */
    }

    @GetMapping(value = "search")
    public void search() {
        List<Book> bs1 = bookService.getBookByIdAndAuthor("g", 6);
        List<Book> bs2 = bookService.getBooksByAuthorStartingWith("a");
        List<Book> bs3 = bookService.getBookByIdAndName("b", 8);
        List<Book> bs4 = bookService.getBooksByPriceGreaterThan(30F);

        Book b = bookService.getMaxIdBook();
        System.out.println("bs1:" + bs1);
        System.out.println("bs2:" + bs2);
        System.out.println("bs3:" + bs3);
        System.out.println("bs4:" + bs4);
        System.out.println("b:" + b);
    }

    @GetMapping(value = "/save")
    public void save() {
        Book book = new Book();
        book.setAuthor("鲁迅");
        book.setName("呐喊");
        book.setPrice(23F);
        bookService.addBook(book);
    }
}
