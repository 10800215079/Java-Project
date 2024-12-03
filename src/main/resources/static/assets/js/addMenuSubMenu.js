		function addMenuAndSubMenu() {
				var storedSubmenuAndMenu = sessionStorage.getItem('submenuAndMenuMaster');
				$('#navbar-nav').empty();

				if (storedSubmenuAndMenu) {
					var submenuAndMenuData = JSON.parse(storedSubmenuAndMenu);

					submenuAndMenuData.forEach(function (menu) {
						// Create the main menu item
						var mainMenu = $('<li class="nav-item menuitem"></li>');
						mainMenu.append('<a class="nav-link menu-link"  data-bs-toggle="collapse" role="button" aria-expanded="true" aria-controls="sidebarDashboards"><i class="ri-dashboard-2-line"></i><span class="menu_id" data-value="' + menu.menuMaster.menuId + '">' + menu.menuMaster.menuName + '</span></a>');

						// Create the submenu container (div)
						var submenuContainer = $('<div class="menu-dropdown collapse" id="menu' + menu.menu_id + '"></div>');

						// Create the submenu list (ul)
						var submenuList = $('<ul class="nav nav-sm flex-column"></ul>');

						// Append submenus to the submenu list
						submenuAndMenuData.forEach(function (submenu) {
							if (menu.menuMaster.menuId === submenu.subMenu.menuId) {
								submenuList.append('<li class="nav-item"><a class="nav-link" href="' + submenu.subMenu.urlRepo + '">' + submenu.subMenu.submenuName + '</a></li>');
							}
						});

						// Append the submenu list to the submenu container
						submenuContainer.append(submenuList);

						// Append the submenu container to the main menu
						mainMenu.append(submenuContainer);

						// Append the main menu to the navbar
						$('#navbar-nav').append(mainMenu);
					});
				}
			}