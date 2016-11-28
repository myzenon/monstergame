<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<title>Monster Battle</title>
	<link rel="stylesheet"
		href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>

</head>
<body ng-class="{'battleModeBG' : ctrl.isBattleState(), 'regModeBG' : ctrl.isState(ctrl.stateVal.REG)}" ng-app="myApp" class="ng-cloak" ng-controller="MonsterController as ctrl">
	<div class="generic-container">
		<div class="panel panel-default">
			<div class="panel-heading">
				<span class="lead">{{ ctrl.isState(ctrl.stateVal.REG) ? 'Monster Registration Form' : 'Monster Battle' }} </span>
				<button ng-show="ctrl.isState(ctrl.stateVal.REG)" ng-click="ctrl.resetBattle()" class="btn btn-danger floatRight">Battle Mode</button>
				<button ng-show="ctrl.isBattleState()" ng-click="ctrl.setState(ctrl.stateVal.REG)" class="btn btn-primary floatRight">Registration Mode</button>
			</div>
			<div class="formcontainer" ng-show="ctrl.isState(ctrl.stateVal.REG)">
				<form ng-submit="ctrl.submit()" name="myForm"
					class="form-horizontal">
					<input type="hidden" ng-model="ctrl.monster.id" />
					<div class="row">
						<div class="form-group col-md-12">
							<label class="col-md-2 control-lable" for="mname">Name</label>
							<div class="col-md-7">
								<input type="text" ng-model="ctrl.monster.monstername" name="mname"
									class="monstername form-control input-sm"
									placeholder="Enter monster's name" required ng-minlength="3" />
								<div class="has-error" ng-show="myForm.mname.$dirty">
									<span ng-show="myForm.mname.$error.required">This is a
										required field</span> <span ng-show="myForm.mname.$error.minlength">Minimum
										length required is 3</span> <span ng-show="myForm.mname.$invalid">This
										field is invalid </span>
								</div>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="form-group col-md-12">
							<label class="col-md-2 control-lable" for="health">Health</label>
							<div class="col-md-7">
								<input type="number" ng-model="ctrl.monster.health" name="health"
									class="health form-control input-sm"
									placeholder="Enter monster's Health." required min="1"/>
								<div class="has-error" ng-show="myForm.health.$dirty">
									<span ng-show="myForm.health.$error.required">This is a
										required field</span> <span ng-show="myForm.health.$error.min">Minimum
										value required is 1</span> <span ng-show="myForm.health.$invalid">This
										field is invalid </span>
								</div>
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="form-group col-md-12">
							<label class="col-md-2 control-lable" for="attack">Attack</label>
							<div class="col-md-7">
								<input type="number" ng-model="ctrl.monster.attack" name="attack"
									class="health form-control input-sm"
									placeholder="Enter monster's Attack." required min="1"/>
								<div class="has-error" ng-show="myForm.attack.$dirty">
									<span ng-show="myForm.attack.$error.required">This is a
										required field</span> <span ng-show="myForm.attack.$error.min">Minimum
										value required is 1</span> <span ng-show="myForm.attack.$invalid">This
										field is invalid </span>
								</div>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="form-actions floatRight">
							<input type="submit" value="{{!ctrl.monster.id ? 'Add' : 'Update'}}"
								class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid">
							<button type="button" ng-click="ctrl.reset()"
								class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Reset
								Form</button>
						</div>
					</div>
				</form>
			</div>

			<div class="panel-notify" ng-bind="ctrl.notify()" ng-show="ctrl.isBattleState()"></div>

			<div class="battlecontainer" class="form-horizontal" ng-show="ctrl.isBattleState()">
				<form>
					<div class="tablecontainer">
						<table class="table">
							<thead>
								<tr>
									<th></th>
									<th>Name</th>
									<th>Health</th>
									<th>Attack</th>
								</tr>
							</thead>
							<tbody>
								<tr ng-class="{'winMonster' : ctrl.isMonsterWin(ctrl.battleMonsters[0]) }">
									<td style="width: 20%; text-align: right; font-weight: bold;">First Monster</td>
									<td><span ng-bind="ctrl.battleMonsters[0].monstername"></span></td>
									<td><span ng-bind="ctrl.battleMonsters[0].health"></span></td>
									<td><span ng-bind="ctrl.battleMonsters[0].attack"></span></td>
								</tr>
								<tr ng-class="{'winMonster' : ctrl.isMonsterWin(ctrl.battleMonsters[1]) }">
									<td style="text-align: right; font-weight: bold;">Second Monster</td>
									<td><span ng-bind="ctrl.battleMonsters[1].monstername"></span></td>
									<td><span ng-bind="ctrl.battleMonsters[1].health"></span></td>
									<td><span ng-bind="ctrl.battleMonsters[1].attack"></span></td>
								</tr>
								<tr>
									<td colspan="4">
										<div class="floatRight">
											<button ng-click="ctrl.battle()" ng-disabled="!ctrl.canBattle()" ng-hide="ctrl.isState(ctrl.stateVal.BATTLE.COMPLETE)" class="btn btn-fight">Fight !!</button>
											<button ng-click="ctrl.resetBattle()" class="btn btn-success">{{ ctrl.isState(ctrl.stateVal.BATTLE.SELECT) ? 'Reset' : 'Finish' }}</button>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</form>
			</div>

		</div>
		<div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="panel-heading">
				<span class="lead">List of Monsters </span>
			</div>
			<div class="tablecontainer">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>ID.</th>
							<th>Name</th>
							<th>Health</th>
							<th>Attack</th>
							<th width="20%"></th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="m in ctrl.monsters">
							<td><span ng-bind="m.id"></span></td>
							<td><span ng-bind="m.monstername"></span></td>
							<td><span ng-bind="m.health"></span></td>
							<td><span ng-bind="m.attack"></span></td>
							<td ng-show="ctrl.isState(ctrl.stateVal.REG)">
								<button type="button" ng-click="ctrl.edit(m.id)"
									class="btn btn-success custom-width">Edit</button>
								<button type="button" ng-click="ctrl.remove(m.id)"
									class="btn btn-danger custom-width">Remove</button>
							</td>
							<td ng-show="ctrl.isBattleState()">
								<button ng-disabled="ctrl.isState(ctrl.stateVal.BATTLE.COMPLETE)" type="button" ng-hide="ctrl.isSelectedBattle(m)" ng-click="ctrl.selectMonsterBattle(m)" class="btn btn-primary">Select</button>
								<button ng-disabled="ctrl.isState(ctrl.stateVal.BATTLE.COMPLETE)" type="button" ng-show="ctrl.isSelectedBattle(m)" ng-click="ctrl.deselectMonsterBattle(m)" class="btn btn-primary active">Deselect</button>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<script
		src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
	<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular-animate.js"></script>
	<script src="<c:url value='/static/js/app.js' />"></script>
	<script src="<c:url value='/static/js/service/monster_service.js' />"></script>
	<script
		src="<c:url value='/static/js/controller/monster_controller.js' />"></script>
</body>
</html>