var DataStructure = {
    Map: function () {
        this._collection = new Object();
        this._count = 0;
        this.put = function (key, value) {
            this._collection[key] = value;
            this._count++;
        };
        this.get = function (key) {
            return this._collection[key];
        };
        this.exist = function (key) {
            if ((typeof this._collection[key] !== "undefined") &&
                (this._collection[key] !== null)) {
                return true;
            }
            return false;
        };
        this.remove = function (key) {
            if ((typeof this._collection[key] !== "undefined") &&
                (this._collection[key] !== null)) {
                this._collection[key] = null;
                this._count--;
                return true;
            }
            return false;
        }
        this.size = function () {
            return this._count;
        };
    },
    Timer: function () {
        this._timeId = 0;
        this._timeValue = new Date();
        this._nodeCounter = new Object();
        this._nodeCounter2 = new Object();

        this.start = function (me, node, node2) {
            this._nodeCounter = node;
            this._nodeCounter2 = node2;
            this._timeId = self.setInterval(function () { me.counter() }, 1000);
        };

        this.stop = function () {
            clearInterval(this._timeId);
        };

        this.counter = function () {
            curDate = new Date();
            if (this._timeValue != 0) {
                v1 = Math.round((curDate - this._timeValue) / 1000);
                sec = v1 % 60;
                v1 = Math.floor(v1 / 60);
                min = v1 % 60;
                hour = Math.floor(v1 / 60);
                if (hour < 10) hour = "0" + hour;
                if (sec < 10) sec = "0" + sec;
                if (min < 10) min = "0" + min;
                this._nodeCounter.html(hour + ":" + min + ":" + sec);
                this._nodeCounter2.html("Video call" + " (" + min + ":" + sec + ")");
            }
        };
    }
};