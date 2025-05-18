package com.example.restaurant.wishlist.service;

import com.example.restaurant.naver.NaverClient;
import com.example.restaurant.naver.dto.SearchImageReq;
import com.example.restaurant.naver.dto.SearchLocalReq;
import com.example.restaurant.wishlist.dto.WishListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishListService {
    private final NaverClient naverClient;

    public WishListDto search(String query) {
        //  지역 검색
        var searchLocalReq = new SearchLocalReq();
        searchLocalReq.setQuery(query);

        var searchLocalResult = naverClient.searchLocal(searchLocalReq);
        if (searchLocalResult.getTotal() > 0) {
            var localItem = searchLocalResult.getItems().stream().findFirst().get();
            var imageQuery = localItem.getTitle().replaceAll("<[^>]*>", "");
            var searchImageReq = new SearchImageReq();
            searchImageReq.setQuery(imageQuery);

            var searchImageResult = naverClient.searchImage(searchImageReq);

            if (searchLocalResult.getTotal() > 0) {
                var imageItem = searchImageResult.getItems().stream().findFirst().get();

                var result = new WishListDto();
                result.setTitle(localItem.getTitle());
                result.setCategory(localItem.getCategory());
                result.setAddress(localItem.getAddress());
                result.setReadAddress(localItem.getRoadAddress());
                result.setHomepageLink(localItem.getLink());
                result.setImageLink(imageItem.getLink());
                return result;
            }
        }

        return new WishListDto();
    }
}
