define('js/util/memory_cache', ['util/cookie'], function(CookieUtil) {
    var cache = {};

    cache._cache = {};
    cache.isLocalStorageSupported = localStorage && localStorage.setItem && typeof(localStorage.setItem) == 'function' ? 1 : 0;
    cache.set = function(key, value) {
        if (!key) {
            return;
        }
        cache._cache[key] = value;
        if (cache.isLocalStorageSupported) {
            var lastCache = JSON.parse(localStorage.getItem('insurance_cache') || '{}');
            lastCache[key] = value;
            if (key == "token") {
                localStorage.setItem('insurance_token', value);
            } else {
                localStorage.setItem('insurance_cache', JSON.stringify(lastCache));
            }
        } else {
            // 保存Cookie的时候，先把值转成String
            var strValue = typeof value !== 'string' ? JSON.stringify(value) : value;
            if (key != "bw") {
                CookieUtil.setCookie(key, strValue, 7);
            }
        }
    }
    cache.get = function(key) {
        if (!key) {
            return;
        }

        if (key == "token") {
            if (cache.isLocalStorageSupported && localStorage.getItem('insurance_token')) {
                return CookieUtil.getCookie('token');
            }
        }

        // 0907 特殊处理productId 因为从银联跳回来localStorage中取到了错误的productId
        if (key == 'productId') {
            return CookieUtil.getCookie('productId');
        }

        if (!cache._cache[key]) {
            if (cache.isLocalStorageSupported) {
                cache._cache = JSON.parse(localStorage.getItem('insurance_cache') || '{}');
            }
        }
        if (cache._cache[key]) {
            return cache._cache[key];
        }
        return CookieUtil.getCookie(key);
    },
    cache.clear = function() {
        if (cache.isLocalStorageSupported) {
            localStorage.setItem('insurance_cache', '{}');
        } else {
            
        }
    }
    return cache;
})