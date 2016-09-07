/**
 * 头
 **/
define('js/element/view/room-box', [
        'text!js/element/template/room-box.tpl'
    ],
    function(RoomTpl, Cache, AlertUI) {
        var LayoutView = Backbone.View.extend({
            events: {

            },
            //
            initialize: function(options, config, events) {
                var t = this;
                t.config = config || {};
                t.disable = t.config.disable;
                t.events = events;
                t.render();
                t.initEvents();
            },
            render: function() {
                var t = this;
                t.$el.append(tpl(RoomTpl, {
                    data: t.config
                }));
                if (t.config.readonly) {
                    t.setReadOnly(true);
                }
            },
            initEvents: function() {
                var t = this;
                if (t.events && t.events.Keyup) {
                    t.$el.find('.' + t.config.fieldName).off('keyup');
                    t.$el.find('.' + t.config.fieldName).on('keyup', function(e) {
                        if ($(e.currentTarget).hasClass(t.config.fieldName) && !t.disable) {
                            t.events.Keyup(e);
                        }
                    });
                }
            },
            getValue: function() {
                var t = this;
                var container = t.$el.find('.' + t.config.fieldName);
                if (container) {
                    var building = container.find('input[name="building"]');
                    var unit = container.find('input[name="unit"]');
                    var room = container.find('input[name="room"]');
                    return {
                        building: building.val(),
                        unit: unit.val(),
                        room: room.val()
                    };
                }
            },
            setValue: function(building, unit, room) {
                var t = this;
                var container = t.$el.find('.' + t.config.fieldName);
                if (container) {

                    var building_input = container.find('input[name="building"]');
                    var unit_input = container.find('input[name="unit"]');
                    var room_input = container.find('input[name="room"]');
                    building_input.val(building);
                    unit_input.val(unit);
                    room_input.val(room);
                }
            },
            setReadOnly: function(value) {
                var t = this;
                var container = t.$el.find('.' + t.config.fieldName);
                if (container) {
                    var input = container.find('input');
                    if (value) {
                        input.attr('readonly', 'readonly');
                    } else {
                        input.removeAttr('readonly');
                    }

                }
            },
            //是否为空
            isVerify: function() {
                var t = this;
                var v = t.getValue();
                return $nvwa.string.isVerify(v);
            }
        });
        return LayoutView;
    });