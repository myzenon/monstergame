'use strict';

App.controller('MonsterController', [
		'$scope',
		'MonsterService',
		function($scope, MonsterService) {
			var self = this;
			self.monster = {
				id : null,
				monstername : '',
				health : '',
				attack : ''
			};
			self.monsters = [];

			self.fetchAllMonsters = function() {
				MonsterService.fetchAllMonsters().then(function(d) {
					self.monsters = d;
				}, function(errResponse) {
					console.error('Error while fetching Monsters');
				});
			};

			self.createMonster = function(monster) {
				MonsterService.createMonster(monster).then(
						self.fetchAllMonsters, function(errResponse) {
							console.error('Error while creating Monster.');
						});
			};

			self.updateMonster = function(monster, id) {
				MonsterService.updateMonster(monster, id).then(
						self.fetchAllMonsters, function(errResponse) {
							console.error('Error while updating Monster.');
						});
			};

			self.deleteMonster = function(id) {
				MonsterService.deleteMonster(id).then(self.fetchAllMonsters,
						function(errResponse) {
							console.error('Error while deleting Monster.');
						});
			};

			self.fetchAllMonsters();

			self.submit = function() {
				if (self.monster.id === null) {
					console.log('Saving New Monster', self.monster);
					self.createMonster(self.monster);
				} else {
					self.updateMonster(self.monster, self.monster.id);
					console.log('Monster updated with id ', self.monster.id);
				}
				self.reset();
			};

			self.edit = function(id) {
				console.log('id to be edited', id);
				for (var i = 0; i < self.monsters.length; i++) {
					if (self.monsters[i].id === id) {
						self.monster = angular.copy(self.monsters[i]);
						break;
					}
				}
			};

			self.remove = function(id) {
				console.log('id to be deleted', id);
				if (self.monster.id === id) {// clean form if the monster to
					// be
					// deleted is shown there.
					self.reset();
				}
				self.deleteMonster(id);
			};

			self.reset = function() {
				self.monster = {
					id : null,
					monstername : '',
					health : '',
					attack : ''
				};
				$scope.myForm.$setPristine(); // reset Form
			};

		} ]);