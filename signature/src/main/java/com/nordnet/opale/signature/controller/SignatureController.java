package com.nordnet.opale.signature.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wordnik.swagger.annotations.Api;

/**
 * Gerer l'ensemble des requetes qui ont en rapport avec les signatures.
 * 
 * @author anisselmane.
 * 
 */
@Api(value = "signature", description = "signature")
@Controller
@RequestMapping("/signature")
public class SignatureController {
}
