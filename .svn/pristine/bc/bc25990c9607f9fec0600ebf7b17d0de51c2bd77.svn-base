/*
 * name: MagicTabs
 * author: Mac_J
 * version: 1.1
 * note: need core.js
 */
mac.tabs = function(self, cfg) {
	cfg = self.config = $.extend({
		speed: 6,
		tabWidth: 90,
		tabHeight: 26,
		hbarHeight: 2
	}, cfg);
	var th = cfg.tabHeight, tw = cfg.tabWidth
		, bh = th + cfg.hbarHeight
		, hx = $('<div class="sbtn"><span></span></div>')
		, hm = $('<div class="main"></div>').height(th)
		, ht = $('<div></div>').height(th);
	hx.width(th).height(th);
	var hb = $('<div class="hbar"></div>');
	hb.height(cfg.hbarHeight).css('top', th);
	var hd = $('<div class="head"></div>').height(th);
	self.append(hb).append(hd);
	var hl = hx.clone().addClass('left')
		, hr = hx.clone().addClass('right');
	hd.append(hl).append(hm.append(ht)).append(hr);
	var bd = $('<div class="body"></div>').appendTo(self);
	self.adjust = function(){
		var sw = self.width(), sh = self.height()
			, h = sh - hd.height();
		bd.height(h-4);
		bd.children('.main').each(function(n, c){
			var ec = $(c), x = ec.attr('height');
			if(!x){
				ec.height(bd.height());
			}else if(x=='auto'){
				ec.css('height', null);
			}else{
				ec.css('height', x);
			}
		})
		var b = ht.width()>hd.width();
		hl.toggle(b);
		hr.toggle(b);
		hm.width(hd.width()-(b? th*2+1: 0));
	}
	function closeTab(a, b, c){
		if(!a.hasClass('closeable'))
			return;
		c = c || a.attr('name');
		b = b || bd.children('[name=' + c + ']');
		if(cfg.onCloseTab && !cfg.onCloseTab(self, c, a))
			return false;
		var s = a.next('.item');
		if(s.length!=1)
			s = a.prev('.item');
		if(s.length!=1)
			return;
		var t = self.selected, t = (t? t.attr('name'):'');
		if(c==t && s) s.click();
		a.remove();
		b.remove();
		ht.width(ht.width()-tw);
		//self.adjust();
	}
	self.closeTab = function(c, a){
		a = a || hd.seek(c);
		closeTab(a, 0, c);
	}
	self.closeTabs = function(x){
		hd.find('.item').each(function(n, a){
			var o = $(a), c = o.attr('name');
			if(c!=x) closeTab(o);
		});
	}
	self.addTab = function(p, n){
		var k = p.code || 'm' + n;
		var a = $('<div class="item normal" name="' + k + '"></div>');
		var b = $('<div class="main hidden" name="' + k + '"></div>');
		if(p.bodyCls)
			b.addClass(p.bodyCls);
		if(p.height)
			b.attr('height', p.height);
		ht.width(ht.width() + tw).append(a.height(th));
		//self.adjust();
		var m = $('<div class="main"></div>');
		a.append('<div class="left"></div>');
		a.width(tw).append(m.width(tw - 14).append(p.title));
		if(p.closeable){
			var x = $('<span class="icon icon-close"></span>');
			var w = $('<div class="xbtn"></div>').append(x);
			x.click(function(){
				closeTab(a, b, k);
				return false;
			});
			m.width(m.width() - 8);
			a.addClass('closeable').append(w);
		}
		a.append('<div class="right"></div>');
		a.click(function() {
			var s = self.selected;
			if (s) {
				s.removeClass("selected");
				bd.seek(s.attr('name')).hide();
			}
			self.selected = a.addClass("selected");
			if(p.url && !b.html() && !p.autoLoad){
				b.load(p.url, p.params, function(){
					if(cfg.onLoadPage)
						cfg.onLoadPage(self, a, b, p);
				});
			}
			var h = b.attr('height');
			if(!h){
				bd.height(self.height() - hd.height() - 4);
			}else if(h=='auto'){
				bd.css('height', h);
			}else{
				bd.css('overflow', 'hidden');
				bd.height(h);
			}
			b.show();
			if(cfg.onShowTab)
				cfg.onShowTab(self, a, b, p);
            //alert('selected');
		});
		bd.append(b.append(p.el));
		if(p.url && p.autoLoad)
			b.load(p.url, p.params, function(){
				if(cfg.onLoadPage)
					cfg.onLoadPage(self, a, b, p);
			});
		return a;
	}
	$.each(cfg.items, function(n, p) {
		self.addTab(p, n);
	});
	self.hscroll = function(){
		var s = cfg.speed * ($(this).hasClass('left')?1:-1);
		$(document).mouseup(function(){
			var t = self.timer;
			if(t) window.clearInterval(t);
		});
		self.timer = window.setInterval(function(){
			var l = hm.scrollLeft();
			hm.scrollLeft(l + s);
		}, 20);
		return self.timer;
	};
	hl.mousedown(self.hscroll);
	hr.mousedown(self.hscroll);
	self.adjust();
	self.scroll = function(a){
		var mis = hd.find('.item');
		hm.scrollLeft(mis.index(a)*tw);
		return self;
	}
	function select(a, c){
		self.scroll(a.click());
		return a;
	}
	self.selectFirst = function() {
		var a = hd.find('.item').first();
		return select(a, a.attr('name'));
	}
	self.select = function(c) {
		return select(hd.seek(c), c);
	}
	return self;
}