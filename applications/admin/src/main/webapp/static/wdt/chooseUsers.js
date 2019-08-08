/**
 * Created by Bob.Bao on 2017/8/10.
 */
(function($){
    var userList = [];
    var $c = $('#choose-users');
    var $content = $c.find('tbody');
    $c.on('click','.com-list', function (e) {
        var $com = $(e.target);
        $com.toggleClass('selected');
    }).on('click','.dept-list li', function (e) {
        e.stopPropagation();
        var deptId = e.target.getAttribute('dept-id');
        var users = _findUsersById(deptId);
        _fillContent(users)
    }).on('click','.confirm', function (e) {
        $c.find(':checked').each(function (i, item) {
            $(this).data('id')
        })
    })
    function _findUsersById(deptId){
        _.find(userList,{id: deptId});
    }
    function _fillContent(users){
        var tpl = '';
        _.each(users, function (u) {
            tpl += '<tr>';
            tpl += '<td><input type="checkbox" data-id="'+ u.id+'"></td>';
            tpl += '<td>'+ u.account+'</td>';
            tpl += '<td>'+ u.name+'</td>';
            tpl += '<td>'+ u.dept+'</td>';
            tpl += '<td>'+ u.post+'</td>';
            tpl += '</tr>';
        })
        $content.append(tpl)
    }
}(jQuery))