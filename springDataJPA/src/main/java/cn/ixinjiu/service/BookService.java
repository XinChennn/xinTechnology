package cn.ixinjiu.service;

import cn.ixinjiu.dao.BookDao;
import cn.ixinjiu.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by XinChen on 2022-08-30
 *
 * @Description:TODO
 */
@Service
public class BookService {
    @Autowired
    BookDao bookDao;

    // save方法由JpaRepository接口提供
    public void addBook(Book book) {
        bookDao.save(book);
    }

    //分页查询
    public Page<Book> getBookByPage(Pageable pageable){
        return bookDao.findAll(pageable);
    }

    public List<Book> getBooksByAuthorStartingWith(String author){
        return bookDao.getBooksByAuthorStartingWith(author);
    }

    public List<Book> getBooksByPriceGreaterThan(Float price){
        return bookDao.getBooksByPriceGreaterThan(price);
    }

    public Book getMaxIdBook(){
        return bookDao.getMaxIdBook();
    }

    public List<Book> getBookByIdAndName(String name, Integer id){
        return bookDao.getBookByIdAndName(name,id);
    }

    public List<Book> getBookByIdAndAuthor(String author,Integer id){
        return bookDao.getBookByIdAndAuthor(author,id);
    }
}
