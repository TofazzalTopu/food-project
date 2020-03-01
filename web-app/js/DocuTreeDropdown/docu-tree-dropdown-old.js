var DocuTreeDropdown = {
    id: "",
    nodeList: [],
    nodeMap: {},
    options:{selectMode:1, optionKey:"key", optionValue:"value"},

    create:function (id, nodeList, options) {
        this.id = id;
        DocuTreeDropdown.nodeList = nodeList;
        if (options) {
            if(!options.optionKey){
                options.optionKey = "key";
            }
            if(!options.optionValue){
                options.optionValue = "value";
            }
            DocuTreeDropdown.options = options;
        }
        DocuTreeDropdown.createInterface();
    },

    createInterface: function(){
        var html = "";
        html += "<div id=\"docu-tree-dropdown-header\" class=\"docu-tree-dropdown-header\">Click</div>";

        html += "<div id=\"docu-tree-dropdown-body\" class=\"docu-tree-dropdown-body\" style=\"display: none; position:absolute;\">";
        html += "<div class=\"docu-tree-dropdown-nav\">";
        html += "<a href=\"javascript:void(0);\" id=\"docu-tree-dropdown-nav-select-all\">Select All</a>";
        html += " | ";
        html += "<a href=\"javascript:void(0);\"  id=\"docu-tree-dropdown-nav-select-none\">Select None</a>";
        html += "<div class=\"docu-tree-dropdown-search-panel\">";
        html += "<span id=\"docu-tree-dropdown-search-button\" class=\"docu-tree-dropdown-search-button\"></span>";
        html += "<input type=\"text\" id=\"docu-tree-dropdown-search-input\" />";
        html += "</div>";
        html += "</div>";

        html += "<div id=\"docu-tree-dropdown-items\" class=\"docu-tree-dropdown-items\" style=\"width:" + DocuTreeDropdown.options.width + "px; height:" + DocuTreeDropdown.options.height + "px;\">";
        html += DocuTreeDropdown.createItems(DocuTreeDropdown.nodeList);
        html += "</div>";
        html += "</div>";

        $('#' + this.id).html(html);

        $('#docu-tree-dropdown-header').click(function(){DocuTreeDropdown.toggle();});
        $('#docu-tree-dropdown-body').mouseover(function(){$('#docu-tree-dropdown-header').addClass('docu-hover');});
        $('#docu-tree-dropdown-body').mouseout(function(){$('#docu-tree-dropdown-header').removeClass('docu-hover');});
        $('#docu-tree-dropdown-nav-select-all').click(function(){DocuTreeDropdown.selectAllItem(true);});
        $('#docu-tree-dropdown-nav-select-none').click(function(){DocuTreeDropdown.selectAllItem(false);});
        $('#docu-tree-dropdown-search-input').click(function(){DocuTreeDropdown.clearSearch();});
        $('#docu-tree-dropdown-search-input').keyup(function(){DocuTreeDropdown.search();});
        $('#docu-tree-dropdown-search-button').click(function(){DocuTreeDropdown.search();});
    },

    createItems:function (nodeList, parentKey) {
        var items = "";
        var item = null;

        parentKey = parentKey ? "docu-tree-dropdown-li-" + parentKey : "";
        for (index in nodeList) {
            item = nodeList[index];
            DocuTreeDropdown.nodeMap[item[DocuTreeDropdown.options.optionKey]] = item;
            if (item.children) {
                if (DocuTreeDropdown.options.selectMode == 1) {
                    items += "<li class=\"docu-tree-dropdown-li " + parentKey + "\"><input type=\"checkbox\" value=\""+item[DocuTreeDropdown.options.optionKey]+"\" class=\"docu-tree-dropdown-checkbox\" onclick=\"DocuTreeDropdown.selectItem(this, '" + item[DocuTreeDropdown.options.optionKey] + "')\" \><span>" + item[DocuTreeDropdown.options.optionValue] + "</span>" + DocuTreeDropdown.createItems(item.children, item[DocuTreeDropdown.options.optionKey]) + "</li>";
                }
                else {
                    items += "<li class=\"docu-tree-dropdown-li " + parentKey + "\"><input type=\"checkbox\" value=\""+item[DocuTreeDropdown.options.optionKey]+"\" class=\"docu-tree-dropdown-checkbox\" \><span>" + item[DocuTreeDropdown.options.optionValue] + "</span>" + DocuTreeDropdown.createItems(item.children, item[DocuTreeDropdown.options.optionKey]) + "</li>";
                }
            }
            else {
                items += "<li class=\"docu-tree-dropdown-li " + parentKey + "\"><input type=\"checkbox\" value=\""+item[DocuTreeDropdown.options.optionKey]+"\" class=\"docu-tree-dropdown-checkbox\" \><span>" + item[DocuTreeDropdown.options.optionValue] + "</span>" + "</li>";
            }
        }
        return "<ul class=\"docu-tree-dropdown-li-container\">" + items + "</ul>";
    },

    toggle:function () {
        if ($("#docu-tree-dropdown-body").is(":visible")) {
            $('#docu-tree-dropdown-body').slideUp();
        }
        else {
            $('#docu-tree-dropdown-body').slideDown();
        }
    },

    selectItem:function (checkbox, key) {
        if ($(checkbox).attr('checked')) {
            $('.docu-tree-dropdown-li-' + key + ' input:checkbox').attr('checked', true);
        }
        else {
            $('.docu-tree-dropdown-li-' + key + ' input:checkbox').attr('checked', false);
        }
    },

    selectAllItem:function (flag) {
        $('.docu-tree-dropdown-li input:checkbox').attr('checked', flag);
    },

    getSelectedNodes: function(){
        var list = [];
        $('.docu-tree-dropdown-checkbox:checked').each(function(){
            list.push(DocuTreeDropdown.nodeMap[this.value]);
        });
        return list;
    },

    getSelectedOptionKeys: function(){
        var list = [];
        $('.docu-tree-dropdown-checkbox:checked').each(function(){
            list.push(this.value);
        });
        return list;
    },

    search: function(){
        DocuTreeDropdown.clearSearch();
        var searchWord = $.trim($('#docu-tree-dropdown-search-input').val());
        if(searchWord == ''){
            return false;
        }
        var words = searchWord.split(' ');
        $('#docu-tree-dropdown-body li span').each(function(index, spanObject){
            for(wordIndex in words){
                if($(spanObject).text().toLowerCase().indexOf(words[wordIndex].toLowerCase()) > -1){
                    $(spanObject).addClass('docu-tree-dropdown-searched-node');
                }
            }
        });
    },

    clearSearch: function(){
        $('#docu-tree-dropdown-body li span').removeClass('docu-tree-dropdown-searched-node');
    }
};
