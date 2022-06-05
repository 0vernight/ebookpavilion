package com.mike;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mike.DTO.BaseResponse;
import com.mike.DTO.BookDTO;
import com.mike.DTO.ItemConverter;
import com.mike.bean.Book;
import com.mike.bean.Shelf;
import com.mike.bean.User;
import com.mike.mapper.UserMapper;
import com.mike.service.UserService;
import com.mike.utils.UUIDUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringBootTest
class EbookpavilionApplicationTests {

    ItemConverter bookShelfMapper = ItemConverter.INSTANCE;

    @Autowired
    User user;
    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() {
    }

    @Test
    public void test1() {
        Book book = new Book();
        book.setId(UUIDUtils.getUUID32())
                .setAuthor("aike")
                .setName("百年孤独");
        Shelf shelf = new Shelf();
        shelf.setAddTime(System.currentTimeMillis())
                .setProgress(10);
        String cutomxx = "xxxxx";
        final BookDTO bookDTO = bookShelfMapper.toBookDTO(book, shelf);
        System.out.println(bookDTO);
    }

    @Test
    public void test2() {
        final BaseResponse register = userService.register(user);
        System.out.println(register);
    }

    @Test
    public void test3() {

        List<BookDTO> resultList = new ArrayList<>();


        List<Book> booksList = getAllBook();
        List<Shelf> shelvesList = getUserShelf("ba94f43a2ece46568629093e6ea7ad9a");

        Map<String, Book> maps = booksList.stream().collect(Collectors.toMap(Book::getId, Function.identity()));
        shelvesList.forEach(shelf -> {
            if (maps.containsKey(shelf.getBookId())) {
                BookDTO bookDTO = bookShelfMapper.toBookDTO(maps.get(shelf.getBookId()), shelf);
                resultList.add(bookDTO);
            }
        });
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        resultList.forEach(item -> {
            System.out.println(simpleDateFormat.format(item.getAddTime()));
            System.out.println(item);
            System.out.println(item.getId());
            System.out.println(item.getAuthor());
            System.out.println(item.getName());
        });

    }

    private List<Book> getAllBook() {
        List<Book> lists = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Book book = new Book();
            book.setId("bookId" + i)
                    .setAuthor("aike")
                    .setName("百年孤独");
            lists.add(book);
        }
        return lists;
    }

    //模拟查找数据库中 用户ID为 userId 的书架表信息
//    select * from shelf where user_id = userId
    private List<Shelf> getUserShelf(String userId) {
        List<Shelf> lists = new ArrayList<>();
        for (int i = 15; i < 45; i++) {
            Shelf shelf = new Shelf();
            shelf.setBookId("bookId" + i);
            shelf.setAddTime(System.currentTimeMillis())
                    .setProgress(10);
            lists.add(shelf);
        }
        return lists;
    }

    @Test
    public void testPage(){
        Integer pageNo = 1;
        Integer pageSize = 5;
        try {
            PageHelper.startPage(pageNo, pageSize);
            List<User> users = userMapper.selectAll();
            PageInfo<User> userPageInfo = new PageInfo<>(users, pageSize);
            System.out.println(userPageInfo.getList());
            System.out.println(userPageInfo.getTotal());
        }finally {
            PageHelper.clearPage();
        }
    }

}
