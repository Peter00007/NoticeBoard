package com.board;

import com.board.dao.NoticeDao;
import com.board.dao.UserDao;
import com.board.dao.impl.NoticeDaoImpl;
import com.board.dao.impl.UserDaoImpl;
import com.board.model.Notice;
import com.board.model.User;
import com.board.model.UserStatus;
import com.board.service.UserService;
import com.board.service.impl.UserServiceImpl;
import com.board.utils.GenerateUserPasswordUtil;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Example {
    public static void main(String[] args) {
        NoticeDao noticeDao = new NoticeDaoImpl();
      //  Notice notice = noticeDao.getById(35);
      //  System.out.println(noticeDao.getById(35));
        /*for (Notice notice: noticeDao.getAllNoticeByUser(8)){
            System.out.println(notice);
        }*/

        /*List<Notice> type = noticeDao.getAllByType(3);
        List<Notice> user = noticeDao.getAllByUser(8);
        Set<Notice> notices = new HashSet<>();
        for (Notice notice: type) {
            notices.add(notice);
        }
        for (Notice notice: user) {
            notices.add(notice);
        }
        notices.addAll(noticeDao.getAllByType(19));
        notices.addAll(noticeDao.getAllByUser(15));
        for (Notice notice: notices) {
            System.out.println(notice);
        }*/
       // System.out.println(GenerateUserPasswordUtil.generatePassword());
       /* String email = "petrofediuk004@";
        boolean b = email.matches("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
        boolean f = email.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&’*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$");
        String regex = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(email);
        System.out.println(match.matches());
        User user = new User();*/

     //  boolean a = "petrofediuk004@gmail.com".matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&’*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$");
      //  System.out.println(a);
        User user = new User();
            UserService userService = new UserServiceImpl();
      //  user.setId(1);
       // boolean b = "petrofediu@gmail.com".matches("([a-z0-9][-a-z0-9_\\+\\.]*[a-z0-9])@([a-z0-9][-a-z0-9\\.]*[a-z0-9]\\.(arpa|root|aero|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel|ac|ad|ae|af|ag|ai|al|am|an|ao|aq|ar|as|at|au|aw|ax|az|ba|bb|bd|be|bf|bg|bh|bi|bj|bm|bn|bo|br|bs|bt|bv|bw|by|bz|ca|cc|cd|cf|cg|ch|ci|ck|cl|cm|cn|co|cr|cu|cv|cx|cy|cz|de|dj|dk|dm|do|dz|ec|ee|eg|er|es|et|eu|fi|fj|fk|fm|fo|fr|ga|gb|gd|ge|gf|gg|gh|gi|gl|gm|gn|gp|gq|gr|gs|gt|gu|gw|gy|hk|hm|hn|hr|ht|hu|id|ie|il|im|in|io|iq|ir|is|it|je|jm|jo|jp|ke|kg|kh|ki|km|kn|kr|kw|ky|kz|la|lb|lc|li|lk|lr|ls|lt|lu|lv|ly|ma|mc|md|mg|mh|mk|ml|mm|mn|mo|mp|mq|mr|ms|mt|mu|mv|mw|mx|my|mz|na|nc|ne|nf|ng|ni|nl|no|np|nr|nu|nz|om|pa|pe|pf|pg|ph|pk|pl|pm|pn|pr|ps|pt|pw|py|qa|re|ro|ru|rw|sa|sb|sc|sd|se|sg|sh|si|sj|sk|sl|sm|sn|so|sr|st|su|sv|sy|sz|tc|td|tf|tg|th|tj|tk|tl|tm|tn|to|tp|tr|tt|tv|tw|tz|ua|ug|uk|um|us|uy|uz|va|vc|ve|vg|vi|vn|vu|wf|ws|ye|yt|yu|za|zm|zw)|([0-9]{1,3}\\.{3}[0-9]{1,3}))");
      //  System.out.println(b);
        user.setName("Aaxcxc");
        user.setLastName("Aaaxcaa");
        user.setEmail("dfdyrsewerfdfdfsdsfrgrg@gmail.com");
        user.setPassword("aaaa");
        user.setUserStatus(UserStatus.CREATED);
      //  ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
       // Validator validator = vf.getValidator();
        userService.create(user);
        // Set<ConstraintViolation<User>> violations = validator.validate(user);
        /*for (ConstraintViolation<User> violation : violations) {
            System.out.println(violation.getMessage());
        }*/
    }
}
