package io.smartir.smartir.website.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class HelperFunctions {
    public <T> Page<T> makingPagination(List<T> givenList, Pageable pageable) {
        if (pageable.getOffset() > givenList.size()) {
            long total = 0L;
            return new PageImpl<>(List.of(), pageable, total);
        }
        if ((pageable.getOffset() <= givenList.size()) && (pageable.getOffset() + pageable.getPageSize() > givenList.size())) {
            var size = givenList.size();
            var givenSubList = givenList.subList((int) pageable.getOffset(), size);
            return new PageImpl<>(givenSubList, pageable, givenList.size());
        }
        var givenSubList = givenList.subList((int) pageable.getOffset(), (int) (pageable.getOffset() + pageable.getPageSize()));
        return new PageImpl<>(givenSubList, pageable, givenList.size());
    }
}
