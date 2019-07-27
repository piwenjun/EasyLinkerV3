package com.easylinker.framework.common.config.security

import cn.hutool.crypto.asymmetric.KeyType
import cn.hutool.crypto.asymmetric.RSA

import java.nio.charset.StandardCharsets

/**
 * @author wwhai* @date 2019/7/26 22:36
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
class RESUtils {

    private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCI74sEJuD/oIDLkuIQwAQoGWdXA4vEdDkbdYEA4Z3sc84my8v/VTkbsSypLYPdK4rzZHNh8dxOCyMfmMSJhhcBnbFHd/xzUmjTlDCf8CsUX5qpJA+66G+lJnor9PhbAodglEKPdVBIyM17HZ6JSzUC9IhoPmWvtSYXeWA3ye543wIDAQAB"
    private static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIjviwQm4P+ggMuS4hDABCgZZ1cDi8R0ORt1gQDhnexzzibLy/9VORuxLKktg90rivNkc2Hx3E4LIx+YxImGFwGdsUd3/HNSaNOUMJ/wKxRfmqkkD7rob6Umeiv0+FsCh2CUQo91UEjIzXsdnolLNQL0iGg+Za+1Jhd5YDfJ7njfAgMBAAECgYAxFDHuFgU0eZFP5WAzAq86tAguzq2Mht9k8ROAfly8v7DWhNqjtCbYvwKzzAVkAtQ1cf5Mz4PXUPSQwY9HfxDFTuk41FRjJ36lotXt2T15aNLnmE84z6BJRdKdTkHUQ+QnoRvxhzofPsgWPGmljL0AarCok3AXQ0kAYUyTk9LLaQJBAPhFadDq4yww4ucKaFX6MYGkh/IGwzJbxVAN7VcDs9+bhxWLpqB1OBitW/Am+RGmp9CTTWIGivt8samhh1tsYjUCQQCNMti4asC/enx9ord5ogpfI/ncEWQZxGQgAyADyrV7b6Rh9Rn+ZQFPmlFqThKGlQvP4keqFq5fOKSpQEa7RlFDAkBK+DsB2UwMNh7ugZgMDmcQrOxYS256MHgeunXnBrk0VrSt7uO8JGlyGjdfB4XCrL6UH0cIyM0DMB4+CQbkto/ZAkAxxvsgCIJq/oNkxzhnaPi/Ea/EJV1kuBeUMYXX0F6qZXFOF1pSlMkhhwWstFlTmLfRUbRyAgz3BMEH6eEFpISBAkEAkkdwKmKgnYrU07blXZ6yGs8uOYtt3BuxxN4DpAy+jUkYsn23GnThOQyfq9kqw8lrhyjG4iOknWfGJCQNFAa0pg=="

    private static final RSA RSA_ATOM = new RSA(PRIVATE_KEY, PUBLIC_KEY)

    static String enRSA(String content) {
        return new String(RSA_ATOM.encrypt(content.bytes, KeyType.PublicKey), StandardCharsets.UTF_8)

    }

    static String deRSA(String content) {

        return new String(RSA_ATOM.decrypt(content.bytes, KeyType.PrivateKey), StandardCharsets.UTF_8)

    }


    static void main(String[] args) {
//         RSA rsa=new RSA()
//         println ("公钥：${rsa.publicKeyBase64}")
//         println ("私钥：${rsa.privateKeyBase64}")
        println(enRSA("Hello world"))

    }
}
