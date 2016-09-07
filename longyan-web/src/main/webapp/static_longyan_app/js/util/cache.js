define('js/util/cache', [], function() {
    $nvwa.cache = {
        //icon cache
        iconCache: null,
        getIconCache: function() {
            return this.iconCache;
        },
        setIconCache: function(icon) {
            _log('caching icon');
            this.iconCache = icon;
            _log(this.iconCache);
        }
    }
    return $nvwa.cache;
});