package io.github.atakanaksit13211.java_dbTest;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService{

    @Override
    public List<Borrowing> getDueBooks(User user) { //list of books that are past the due date
        if(user.isBorrowingsEmpty()){
            return Collections.emptyList();
        }

        return user.getBorrowings().stream()
                .filter(borrowing -> borrowing.getDue_timestamp().isAfter(ZonedDateTime.now()))
                .collect(Collectors.toCollection(List::of));
    }


}
