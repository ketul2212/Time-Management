package com.ketul.resource;

import com.ketul.module.TimeTable;
import com.ketul.module.dto.RequestDtoForTimeTable;
import com.ketul.service.TimeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TimeTableResource {

    @Autowired
    private TimeTableService timeTableService;

    // section Create TimeTable API
    @PostMapping("/create-TimeTable")
    public TimeTable createTimeTable(@RequestBody RequestDtoForTimeTable requestDtoForTimeTable) {
        return timeTableService.createTimeTable(requestDtoForTimeTable);
    }

    // section Update TimeTable API
    @PutMapping("/updateTimeTable/{id}")
    public TimeTable updateTimeTable(@PathVariable long id, @RequestBody RequestDtoForTimeTable requestDtoForTimeTable) {
        return timeTableService.updateTimeTable(id, requestDtoForTimeTable);
    }

    // section Complete Task
    @PutMapping("/complete-task/{id}")
    public TimeTable completeTask(@PathVariable long id) {
        return timeTableService.completeTask(id);
    }

    // section Get All Tasks API
    @GetMapping("/tasks")
    public List<TimeTable> getAllTasks() {
        return timeTableService.getAllTasks();
    }
}
