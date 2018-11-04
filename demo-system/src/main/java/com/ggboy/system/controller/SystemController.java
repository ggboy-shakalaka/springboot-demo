package com.ggboy.system.controller;

import com.ggboy.common.constant.ErrorCodeConstant;
import com.ggboy.common.constant.PropertiesConstant;
import com.ggboy.common.constant.RedisConstant;
import com.ggboy.common.domain.FrontEndResponse;
import com.ggboy.common.exception.CommonUtilException;
import com.ggboy.common.exception.InternalException;
import com.ggboy.common.redis.RedisWrapper;
import com.ggboy.common.utils.BaseRSA;
import com.ggboy.system.enums.KeyType;
import com.ggboy.system.service.CityService;
import com.ggboy.system.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@ResponseBody
@RequestMapping("/system")
public class SystemController {
    @Autowired
    private RedisWrapper redisWrapper;
    @Autowired
    private CityService cityService;
    @Autowired
    private SequenceService sequenceService;

    @GetMapping("/publicKey")
    public FrontEndResponse publicKey() {
        try {
            byte[] publicKey = redisWrapper.getBytes(RedisConstant.REDIS_PUBLIC_KEY);
            if (publicKey == null) {
                Map<String, Object> keyMap = BaseRSA.genKeyPair();
                publicKey = BaseRSA.getPublicKey(keyMap);
                redisWrapper.set(RedisConstant.REDIS_PUBLIC_KEY, publicKey, 60 * 5);
                redisWrapper.set(RedisConstant.REDIS_PRIVATE_KEY, BaseRSA.getPrivateKey(keyMap), 60 * 5);
            }
            return new FrontEndResponse(Base64Utils.encodeToString(publicKey));
        } catch (CommonUtilException e) {
            throw new InternalException(ErrorCodeConstant.RSA_ERROR, "rsa generate error");
        }
    }

    @GetMapping("/provinces")
    public FrontEndResponse provinces() {
        return new FrontEndResponse(cityService.getProvinces());
    }

    @GetMapping("/cities")
    public FrontEndResponse cities(int provinceId) {
        return new FrontEndResponse(cityService.getCities(Integer.valueOf(provinceId)));
    }

    @GetMapping("/allCities")
    public FrontEndResponse allCities() {
        return new FrontEndResponse(cityService.getCities());
    }

    @GetMapping("/getPrimaryId")
    public String getPrimaryId(KeyType keyType) {
        Long number = sequenceService.next(PropertiesConstant.getSequenceDefaultName());
        if (number == null)
            return null;
        return convert(keyType, number);
    }

    private String convert(KeyType keyType, Long number) {
        String numStr = String.format("%05d", number);
        return keyType == null ? "" : keyType.getFalg() + System.currentTimeMillis() / 1000 + numStr.substring(numStr.length() - 5);
    }
}
