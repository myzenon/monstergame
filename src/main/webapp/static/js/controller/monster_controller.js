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

			// My implementation.

			self.monsterModel = function() {
				return {
                    id : null,
                    monstername : '',
                    health : '',
                    attack : ''
                };
			}

			self.stateVal = {
				REG: 0,
				BATTLE : {
                    SELECT : 1,
                    COMPLETE : 2
				}
			};

			self.state = self.stateVal.REG;

			self.setState = function(state) {
				self.state = state;
			};

			self.isState = function(state) {
				return self.state === state;
			};

			self.isBattleState = function () {
				return (self.state === self.stateVal.BATTLE.SELECT) || (self.state === self.stateVal.BATTLE.COMPLETE);
            };


			self.battleMonsters = [];

            self.battleMonsters = [self.monsterModel(), self.monsterModel()];
            self.winMonster = self.monsterModel();

			self.resetBattle = function () {
				self.battleMonsters = [self.monsterModel(), self.monsterModel()];
            	self.winMonster = self.monsterModel();
                self.state = self.stateVal.BATTLE.SELECT;
            };


			self.isSelectedBattle = function(monster) {
				for(var i = 0; i < self.battleMonsters.length; i++) {
					if(self.battleMonsters[i].id === monster.id) {
						return true;
					}
				}
				return false;
            }

            self.selectMonsterBattle = function(monster) {
				if(self.battleMonsters[0].id === null) {
					self.battleMonsters[0] = monster;
					return null;
				}
                if(self.battleMonsters[1].id === null) {
                    self.battleMonsters[1] = monster;
                    return null;
                }
                alert('You can pick up only 2 monsters for the battle.');
			}

            self.deselectMonsterBattle = function(monster) {
                if(self.battleMonsters[0].id === monster.id) {
                    self.battleMonsters[0] = {
                        id : null,
                        monstername : '',
                        health : '',
                        attack : ''
                    };
                    return null;
                }
                if(self.battleMonsters[1].id === monster.id) {
                    self.battleMonsters[1] = {
                        id : null,
                        monstername : '',
                        health : '',
                        attack : ''
                    };
                    return null;
                }
            }

			self.notify = function() {
				if(self.winMonster.id != null) {
					return "Winner is " + self.winMonster.monstername;
                }
				if(self.battleMonsters[0].id === null) {
					return "Select the first monster to battle !!";
				}
                else if(self.battleMonsters[1].id === null) {
                    return "Select the second monster to battle !!";
                }
                else {
					return "Now we are ready to fight !! clicks on Fight button."
				}
			};

			self.battle = function () {
				MonsterService.battle(self.battleMonsters).then(function (winner) {
					self.winMonster = winner;
                });
				self.state = self.stateVal.BATTLE.COMPLETE;
            };

			self.canBattle = function () {
				return self.battleMonsters[0].id !== null && self.battleMonsters[1].id !== null;
            };

			self.isMonsterWin = function(monster) {
				if(self.winMonster.id === null) {
					return false;
				}
				return monster.id === self.winMonster.id;
			}


		} ]);