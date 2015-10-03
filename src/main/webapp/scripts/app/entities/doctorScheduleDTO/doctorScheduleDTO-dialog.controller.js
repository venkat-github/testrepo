'use strict';

angular.module('hipster1App').controller('DoctorScheduleDTODialogController',
    ['$scope', '$stateParams', '$state', 'entity', 'hospitals', 'DoctorScheduleDTO',
        function($scope, $stateParams, $state, entity, hospitals, DoctorScheduleDTO) {

        $scope.doctorScheduleDTO = entity;
        
        angular.forEach(hospitals, function(value, key) {
                      alert(key);});

        $scope.hospitals2 = hospitals;
        
        $scope.times = [{name:"8:00"}, {name:"8:30"},{name:"9:00"}];
        $scope.load = function(id) {
            DoctorScheduleDTO.get({id : id}, function(result) {
                $scope.doctorScheduleDTO = result;

            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('hipster1App:doctorScheduleDTOUpdate', result);
            //$modalInstance.close(result);
            $state.go('doctorScheduleDTO');
        };

        $scope.save = function () {
            if ($scope.doctorScheduleDTO.id != null) {
                DoctorScheduleDTO.update($scope.doctorScheduleDTO, onSaveFinished);
            } else {
                DoctorScheduleDTO.save($scope.doctorScheduleDTO, onSaveFinished);
            }
        };
/*
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
*/

              $scope.today = function() {
            $scope.doctorScheduleDTO.startDate = new Date();
            $scope.doctorScheduleDTO.endDate = new Date();
        };
        $scope.today();

  $scope.clear = function () {
    $scope.dt = null;
  };

  
  $scope.open2 = function($event) {
    $scope.opened2 = true;
  };

$scope.open1 = function($event) {
    $scope.opened1 = true;
  };

  $scope.dateOptions = {
    formatYear: 'yy',
    startingDay: 1
  };

  $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
  $scope.format = $scope.formats[0];

  $scope.opened1 = false;
    $scope.opened2 = false;
  $scope.getDayClass = function(date, mode) {
    if (mode === 'day') {
      var dayToCheck = new Date(date).setHours(0,0,0,0);

      for (var i=0;i<$scope.events.length;i++){
        var currentDay = new Date($scope.events[i].date).setHours(0,0,0,0);

        if (dayToCheck === currentDay) {
          return $scope.events[i].status;
        }
      }
    }

    return '';
    };
  
}]);
