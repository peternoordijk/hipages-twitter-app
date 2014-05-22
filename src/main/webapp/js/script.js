(function($) {
	var Stream = Class
			.extend({
				addStatus : function(data) {
					var status = $('<li class="status list-group-item"></li>');
					status.append($('<span class="badge">'
							+ timeToString(data.date,
									function(year, month, day, hours, minutes,
											seconds) {
										return (day + "/" + month + "/" + year
												+ " " + hours + ":" + minutes
												+ ":" + seconds);
									}) + '</span>'));
					status.append('<p>' + data.text + '</p>');
					if (data.images.length > 0) {
						var carouselContainer = $('<div class="carousel slide" data-ride="carousel"></div>');
						var indicators = $('<ol class="carousel-indicators"></ol>');
						var wrapper = $('<div class="carousel-inner"></div>')
						
						for (var a = 0; a < data.images.length; a++) {
							var imgData = data.images[a];
							var indicator = $('<li data-target="#carousel-example-generic" data-slide-to="0"' + (a == 0 ? ' class="active"' : '') + '></li>')
							var slide = $('<div class="item'+ (a == 0 ? ' active' : '') + '"><img src="'+ imgData.src +'"></div>');
							
							wrapper.append(slide);
							indicators.append(indicator);
						}
						
						carouselContainer.append(wrapper);
						if (data.images.length > 1) { 
							carouselContainer.append(indicators);
							carouselContainer.append('<a class="left carousel-control" href="#carousel-example-generic" data-slide="prev"><span class="glyphicon glyphicon-chevron-left"></span></a><a class="right carousel-control" href="#carousel-example-generic" data-slide="next"><span class="glyphicon glyphicon-chevron-right"></span></a>');
							carouselContainer.carousel();
						}
						status.append(carouselContainer);
					}
					this.rootElement.append(status);
				},
				init : function(parent, api, params) {
					this.api = api;
					this.params = params;
					parent = $(parent);
					parent.empty();
					this.items = [];
					this.rootElement = $('<ul class="list-group"></ul>');
					this.loadMoreButton = $('<a class="alert btn btn-default" role="button"></a>');

					this.loadContainer = $('<li class="btn-group btn-group-justified"></li>');
					this.loadContainer.append(this.loadMoreButton);
					this.rootElement.append(this.loadContainer);
					parent.append(this.rootElement);
					this.requestItems();
				},
				requestItems : function() {
					this.setLoadButton("Loading...", "alert-info", null);
					var str = this;
					$.getJSON(this.api, this.params)
						.done(function(a){
							str.loadItems(a);
						})
						.fail(function(e){
							str.onfail(e);
						});
				},
				loadItems: function (data) {
					this.items = this.items.concat(data);
					for (var i = 0; i < data.length; i++) {
						this.addStatus(data[i]);
					}
					if (data.length < this.params.count) {
						this.setLoadButton("No more items", "alert-success", null);
					} else {
						this.setLoadButton("Load more", "alert-success", 'loadMore');
					}
					
				},
				onfail : function(e) {
					console.error(e.message);
					this.setLoadButton("Oops! Something went wrong loading this stream. Ctrl+shift+J for more info", "alert-danger", null);
				},
				setLoadButton: function (text, className, handler) {
					this.loadMoreButton.html(text);
					this.loadMoreButton.removeClass("alert-success");
					this.loadMoreButton.removeClass("alert-info");
					this.loadContainer.appendTo(this.rootElement);
					this.loadMoreButton.addClass(className);
					this.loadMoreButton.unbind('click');
					var str = this;
					this.loadMoreButton.click(function () {
						if (handler) str[handler]();
					});
				},
				loadMore: function () {
					var lastStatus = this.items[this.items.length - 1]; 
					this.params.maxId = lastStatus.id;
					this.requestItems();
				}
			}),
			
			UserStream = Stream.extend({
				init: function (parent, user) {
					this._super(parent, 'webapi/user', {
						user: user,
						count: 20
					});
				}
			}),
	
			QueryStream = Stream.extend({
				init: function (parent, query) {
					this._super(parent, 'webapi/query', {
						query: query,
						count: 20
					});
				}
			}),
			
			createQueryStream = function (query) {
				window.mainStream = new QueryStream('#mainstream', query);
			},
			
			createUserStream = function () {
				window.mainStream = new UserStream('#mainstream', 'hipages');
			};
			

	$(function() {
		$('#nav-home').click(function(e){
			e.preventDefault();
			createUserStream();
		});
		$('#nav-hipages').click(function(e){
			e.preventDefault();
			createQueryStream('@hipages');
		});
		$('#nav-search').submit(function(e){
			e.preventDefault();
			createQueryStream($('#nav-search-input').val());
		});
		createUserStream();
	});
})(jQuery);