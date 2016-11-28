'use strict';
 
App.factory('MonsterService', ['$http', '$q', function($http, $q){
 
    return {
         
            fetchAllMonsters: function() {
                    return $http.get('http://localhost:8080/monstergame/monster/')
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while fetching monsters');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
             
            createMonster: function(monster){
                    return $http.post('http://localhost:8080/monstergame/monster/', monster)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while creating monster');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
             
            updateMonster: function(monster, id){
                    return $http.put('http://localhost:8080/monstergame/monster/'+id, monster)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while updating monster');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
             
            deleteMonster: function(id){
            	return $http.delete('http://localhost:8080/monstergame/monster/'+id)
            			.then(
            					function(response){
                                    return response.data;
                                }, 
                                function(errResponse){
                                    console.error('Error while deleting monster');
                                    return $q.reject(errResponse);
                                }
            			);
            },

            battle : function(monsters) {
                return $http.post('http://localhost:8080/monstergame/monster/battle/', monsters)
                    .then(
                        function(response){
                            return response.data;
                        },
                        function(errResponse){
                            console.error('Error while battle monster');
                            return $q.reject(errResponse);
                        }
                    );
            }
         
    };
 
}]);