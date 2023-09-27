package com.example.demo.infrastructure.api.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.reactive.result.view.RedirectView
import springfox.documentation.annotations.ApiIgnore

@Controller
class HomeController {

    @ApiIgnore
    @GetMapping("/")
    fun home(): RedirectView {
        return RedirectView("/swagger-ui/")
    }
}
