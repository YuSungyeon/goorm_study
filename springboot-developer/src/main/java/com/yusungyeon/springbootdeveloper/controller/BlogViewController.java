package com.yusungyeon.springbootdeveloper.controller;

import com.yusungyeon.springbootdeveloper.domain.Article;
import com.yusungyeon.springbootdeveloper.dto.ArticleListViewResponse;
import com.yusungyeon.springbootdeveloper.dto.ArticleViewResponse;
import com.yusungyeon.springbootdeveloper.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogViewController {

    private final BlogService blogService;

    @GetMapping("/articles")
    public String getArticles(Model model) {
        List<ArticleListViewResponse> articles = blogService.findAll().stream()
                .map(ArticleListViewResponse::new)
                .toList();

        model.addAttribute("articles", articles);

        return "articleList";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        Article article = blogService.findById(id);
        model.addAttribute("article", new ArticleViewResponse(article));

        return "article";
    }

    @GetMapping("/new-article")
    // id 키를 가진 퀴리 파라미터의 값을 id 변수에 매핑
    public String newArticle(@RequestParam(required = false) Long id, Model model) {
        if (id == null) { // id 값 없으면 생성
            model.addAttribute("article", new ArticleViewResponse());
        } else { //있으면 수정
            Article article = blogService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article));
        }

        return "newArticle";
    }
}
