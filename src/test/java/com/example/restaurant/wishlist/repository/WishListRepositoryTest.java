package com.example.restaurant.wishlist.repository;

import com.example.restaurant.wishlist.entity.WishListEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WishListRepositoryTest {

    @Autowired
    private WishListRepository wishListRepository;
    private WishListEntity create() {
        var wishList = new WishListEntity();
        wishList.setTitle("title");
        wishList.setCategory("category");
        wishList.setAddress("address");
        wishList.setReadAddress("readAddress");
        wishList.setImageLink("");
        wishList.setHomepageLink("");
        wishList.setVisit(false);
        wishList.setVisitCount(0);
        wishList.setLastVisitDate(null);
        return wishList;
    }

    @Test
    public void saveTest() {
        var wishListEntity = create();
        var expected = wishListRepository.save(wishListEntity);

        Assertions.assertNotNull(expected);
        Assertions.assertEquals(1, expected.getIndex());
    }

    @Test
    public void updateTest() {
        var wishListEntity = create();
        var expected = wishListRepository.save(wishListEntity);

        expected.setTitle("update test");
        var saveEntity = wishListRepository.save(expected);

        Assertions.assertEquals("update test", saveEntity.getTitle());
        Assertions.assertEquals(1, wishListRepository.listAll().size());

    }
    @Test
    public void findByIdTest() {
        var wishListEntity = create();
        wishListRepository.save(wishListEntity);

        var expected = wishListRepository.findById(1);
        Assertions.assertTrue(expected.isPresent());
        Assertions.assertEquals(1, expected.get().getIndex());
    }

    @Test
    public void deleteByIdTest() {
        var wishListEntity = create();
        wishListRepository.save(wishListEntity);

        wishListRepository.deleteById(1);
        var expected = wishListRepository.findById(1);
        Assertions.assertFalse(expected.isPresent());
    }

    @Test
    public void listAllTest() {
        var wishListEntity1 = create();
        wishListRepository.save(wishListEntity1);
        var wishListEntity2 = create();
        wishListRepository.save(wishListEntity2);

        int count = wishListRepository.listAll().size();
        Assertions.assertEquals(2, count);
    }
}
