/*通用js方法封装*/

var Len = {

    /**
     * 添加
     * @param url 必填
     * @param title
     * @param id
     */
    add: function (url, title, id) {
        this.popup(url, this.isEmpty(title) ? '添加' : title, 700, 450, id)
    },

    /**
     * 更新
     * @param url 必填
     * @param title
     * @param id
     */
    update: function (url, title, id) {
        this.popup(url, this.isEmpty(title) ? '修改' : title, 700, 450, id)
    },

    /**
     * 详情
     * @param url 必填
     * @param title
     * @param id
     */
    detail: function (url, title, id) {
        this.popup(url + '&detail=true', this.isEmpty(title) ? '详情' : title, 700, 450, id)
    },

    /**
     * 根据id删除
     * @param url 必填
     * @param id 必填
     * @param callback 必填
     * @param tableId 刷新界面的tableid
     * @param name
     */
    delete: function (url, id, callback, tableId, name) {
        var extendTitle = Len.isEmpty(name) ? '' : '[<label style="color: #00AA91;">' + name + '</label>]';
        layer.confirm('确定删除' + extendTitle + '?', function () {
            if (Len.isFunction(callback)) {
                Len.ajaxPost(url, {id: id}, callback);
            } else {
                Len.ajaxPost(url, {id: id}, function (res) {
                    Len.rbSuccess("删除成功");
                    if (!Len.isEmpty(tableId)) {
                        layui.table.reload(tableId);
                    }
                });
            }
        });
    },

    /**
     * 中心弹框 成功提示
     * @param msg
     * @param isTop 顶层弹框
     */
    success: function (msg, isTop) {
        if (isTop) {
            window.top.layer.msg(msg, {icon: 6});
        } else {
            layer.msg(msg, {icon: 6});
        }
    },

    /**
     * 中心弹框 错误提示
     * @param msg
     * @param isTop 顶层弹框
     */
    error: function (msg, isTop) {
        if (isTop) {
            window.top.layer.msg(msg, {icon: 5});
        } else {
            layer.msg(msg, {icon: 5});
        }
    },

    /**
     * 中心弹框 警告提示
     * @param msg
     * @param isTop 顶层弹框
     */
    warn: function (msg, isTop) {
        if (isTop) {
            window.top.layer.msg(msg, {icon: 4});
        } else {
            layer.msg(msg, {icon: 4});
        }
    },

    /**
     * 右下角弹框 成功提示
     * @param msg
     * @param isTop 顶层弹框
     */
    rbSuccess: function (msg, isTop) {
        if (isTop) {
            window.top.layer.msg(msg, {icon: 6, offset: 'rb', area: ['120px', '80px'], anim: 2});
        } else {
            layer.msg(msg, {icon: 6, offset: 'rb', area: ['120px', '80px'], anim: 2});
        }
    },

    /**
     * 右下角错误提示
     * @param msg
     * @param isTop 顶层弹框
     */
    rbError: function (msg, isTop) {
        if (isTop) {
            window.top.layer.msg(msg, {icon: 5, offset: 'rb', area: ['120px', '80px'], anim: 2});
        } else {
            layer.msg(msg, {icon: 5, offset: 'rb', area: ['120px', '80px'], anim: 2});
        }
    },

    /**
     * 右下角异常提示
     * @param msg
     * @param isTop 顶层弹框
     */
    rbWarn: function (msg, isTop) {
        if (isTop) {
            window.top.layer.msg(msg, {icon: 4, offset: 'rb', area: ['120px', '80px'], anim: 2});
        } else {
            layer.msg(msg, {icon: 4, offset: 'rb', area: ['120px', '80px'], anim: 2});
        }
    },

    /**
     * 通用弹框
     * @param url 请求url
     * @param title 弹框标题
     * @param w 宽
     * @param h 高
     * @param id
     */
    popup: function (url, title, w, h, id) {
        title = Len.isEmpty(title) ? false : title;
        url = Len.isEmpty(url) ? "error/404" : url;
        w = Len.isEmpty(w) ? ($(window).width() * 0.9) : w;
        h = Len.isEmpty(h) ? ($(window).height() - 50) : h;
        window.top.layer.open({
            id: id,
            type: 2,
            area: [w + 'px', h + 'px'],
            fix: false,
            maxmin: true,
            shadeClose: true,
            shade: 0.4,
            title: title,
            content: url
        });
    },

    layerAjax: function (url, data, tableId) {
        $.ajax({
            url: url,
            type: 'post',
            data: data,
            traditional: true,
            success: function (d) {
                parent.layer.close(parent.layer.getFrameIndex(window.name));
                window.parent.document.getElementById(parent.getId()).contentWindow.table.reload(tableId);
                var msg = d.msg;
                if (Len.isEmpty(d.msg)) {
                    msg = "成功";
                }
                window.top.layer.msg(msg, {icon: 6, offset: 'rb', area: ['200px', '80px'], anim: 2});
            }, error: function (e) {
                layer.alert("发生错误", {icon: 6}, function () {
                    parent.layer.close(parent.layer.getFrameIndex(window.name));
                });
            }
        });
    },

    eleClick: function (active, ele) {
        $(ele).on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    },

    /**
     * 通用post
     * @param url
     * @param data
     * @param callback 回调
     */
    ajaxPost: function (url, data, callback) {
        $.ajax({
            url: url,
            type: "post",
            data: data,
            success: function (res) {
                callback(res);
            }
        });
    },

    /**
     * 回车
     * @param active
     */
    keydown: function (active) {
        document.onkeydown = function (e) {
            var theEvent = window.event || e;
            var code = theEvent.keyCode || theEvent.which;
            if (code === 13) {
                active.select();
            }
        }
    },
    /**
     * 按钮绑定
     * @param active
     */
    btnBind: function (active) {
        $('.len-form-item .layui-btn,.layui-btn-group .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    },

    /**
     * 表单绑定 回车搜索和按钮事件
     * @param active
     */
    formBind: function (active) {
        Len.keydown(active);
        Len.btnBind(active);
    },

    /**
     * 空 返回true
     * @param value 值
     * @param allowEmptyString 是否允许空字符串
     * @returns {boolean|arg is Array<any>}
     */
    isEmpty: function (value, allowEmptyString) {
        return (value == null) || (!allowEmptyString ? value === '' : false) || (Len.isArray(value) && value.length === 0);
    },
    /**
     * array类型 返回true
     */
    isArray: ('isArray' in Array) ? Array.isArray : function (value) {
        return toString.call(value) === '[object Array]';
    },
    /**
     * 时间类型 返回true
     * @param value
     * @returns {boolean}
     */
    isDate: function (value) {
        return toString.call(value) === '[object Date]';
    },
    /**
     *
     * @param value
     * @returns {boolean}
     */
    isMSDate: function (value) {
        if (!Len.isString(value)) {
            return false;
        }
        return /^\\?\/Date\(([-+])?(\d+)(?:[+-]\d{4})?\)\\?\/$/.test(value);
    },
    /**
     * object对象 返回true
     */
    isObject: (toString.call(null) === '[object Object]') ? function (value) {
        // check ownerDocument here as well to exclude DOM nodes
        return value !== null && value !== undefined && toString.call(value) === '[object Object]' && value.ownerDocument === undefined;
    } : function (value) {
        return toString.call(value) === '[object Object]';
    },
    /**
     * js原始值 返回true
     * @param value
     * @returns {boolean}
     */
    isPrimitive: function (value) {
        var type = typeof value;
        return type === 'string' || type === 'number' || type === 'boolean';
    },
    /**
     * js function 返回true
     */
    isFunction: // Safari 3.x and 4.x returns 'function' for typeof <NodeList>, hence we need to fall back to using
    // Object.prototype.toString (slower)
        (typeof document !== 'undefined' && typeof document.getElementsByTagName('body') === 'function') ? function (value) {
            return !!value && toString.call(value) === '[object Function]';
        } : function (value) {
            return !!value && typeof value === 'function';
        },
    /**
     * 数字 返回true
     * @param value
     * @returns {boolean}
     */
    isNumber: function (value) {
        return typeof value === 'number' && isFinite(value);
    },
    /**
     * 数字或数字字符串返回true
     * @param value
     * @returns {boolean}
     */
    isNumeric: function (value) {
        return !isNaN(parseFloat(value)) && isFinite(value);
    },
    /**
     * 字符串类型 返回true
     * @param value
     * @returns {boolean}
     */
    isString: function (value) {
        return typeof value === 'string';
    },
    /**
     * boolean类型 返回true
     * @param value
     * @returns {boolean}
     */
    isBoolean: function (value) {
        return typeof value === 'boolean';
    },
    /**
     * undefined类型 返回true
     * @param value
     * @returns {boolean}
     */
    isDefined: function (value) {
        return typeof value !== 'undefined';
    },
    /**
     * 根据id获取dom
     * @param id
     * @returns {HTMLElement}
     */
    getElementById: function (id) {
        return document.getElementById(id);
    },
    /**
     * 判断是否为一行
     * @param length
     * @returns {boolean}
     */
    onlyOne: function (length) {
        if (length !== 1) {
            layer.msg('请选择一行编辑,已选[' + length + ']行', {icon: 5});
            return false;
        }
        return true;
    },

    init: function () {
        /**
         * 全局业务异常捕获 未知异常捕获
         */
        layui.jquery.ajaxSetup({
            beforeSend: function (xhr, options) {
                var originalSuccess = options.success;
                options.success = function (data, textStatus, jqXhr) {
                    if (Len.isBoolean(data.flag) && !data.flag) {
                        Len.error(data.msg);
                        return false;
                    }
                    originalSuccess.apply(this, arguments)
                };
            }
        });
    }
};

Len.init();