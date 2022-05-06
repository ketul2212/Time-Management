package com.ketul.service;

import com.ketul.module.TimeTable;
import com.ketul.module.User;
import com.ketul.module.dto.RequestDtoForTimeTable;
import com.ketul.repo.TimeTableRepository;
import com.ketul.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TimeTableService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TimeTableRepository timeTableRepository;

    @Autowired
    private UserService userService;

    // section create Time-Table
    @Transactional
    public TimeTable createTimeTable(RequestDtoForTimeTable requestDtoForTimeTable) {
        User user = userService.login(requestDtoForTimeTable.getEmail(), requestDtoForTimeTable.getPass());
        System.out.println(user);

        if(user != null) {
            TimeTable timeTable = new TimeTable();
            timeTable.setTitle(requestDtoForTimeTable.getTitle());
            timeTable.setDescription(requestDtoForTimeTable.getDescription());
            timeTable.setEndTask(requestDtoForTimeTable.getEndTask());
//            timeTable.setUser(user);
            List<TimeTable> listTimeTable = user.getTimeTables();
            if (listTimeTable == null)
                listTimeTable = new ArrayList<>();
            listTimeTable.add(timeTable);
            user.setTimeTables(listTimeTable);
            System.out.println("list : -> " + user.getTimeTables());
            userRepository.save(user);
//            return timeTableRepository.save(timeTable);
            return timeTable;
        }
        return new TimeTable();
    }

    // section update TimeTable
    public TimeTable updateTimeTable(long id, RequestDtoForTimeTable requestDtoForTimeTable) {
        TimeTable timeTable = timeTableRepository.findById(id).orElse(null);

        if (timeTable != null) {
            timeTable.setTitle(requestDtoForTimeTable.getTitle());
            timeTable.setDescription(requestDtoForTimeTable.getDescription());
            timeTable.setEndTask(requestDtoForTimeTable.getEndTask());
            return timeTableRepository.save(timeTable);
        }
        return new TimeTable();
    }

    // section Complete Task
    public TimeTable completeTask(long id) {
        TimeTable timeTable = timeTableRepository.getById(id);
        timeTable.setComplete(true);
        return timeTableRepository.save(timeTable);
    }

    // section Get All Tasks API
    public List<TimeTable> getAllTasks() {
        return timeTableRepository.findAll();
    }

    // section User tasks API
    public List<TimeTable> getUserTasks(long id) {
        User user = userRepository.findById(id).orElse(null);

        if(user != null) {
            System.out.println(user.getTimeTables());
            return user.getTimeTables();
        }

        return new ArrayList<>();
    }
}
