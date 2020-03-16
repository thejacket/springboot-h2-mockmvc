package com.thejacket.springbootH2Mockmvc.controller;

import com.thejacket.springbootH2Mockmvc.model.Mission;
import com.thejacket.springbootH2Mockmvc.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/* REST Controller for interacting with Mission entities
*  GET method: /missions[?id=] Returns mission of given id or returns all missions if none supplied.
*               Access by manager only
*  POST methods: /mission {data-json} Returns SUCCESS and saves to database if data was properly given in JSON format
*                /missions {data-json} Return SUCCESS and saves to database - for multiple records
*  DELETE methods: /missions Returns SUCCESS and deletes all missions' records from database
*                  /mission[?id=] Returns SUCCESS and deletes mission of given id
*  PUT method: /mission {data-json} Returns updated mission as JSON
*
* */

@RestController
public class MissionController {

    @Autowired
    private MissionService missionService;


    @RequestMapping(value = "/mission", method = RequestMethod.POST)
    String addMission(@RequestBody Mission mission){
        Mission savedMission = missionService.save(mission);
        return "SUCCESS";
    }

    @RequestMapping(value = "/mission", method = RequestMethod.PUT)
    Mission updateMission(@RequestBody Mission mission){
        Mission updatedMission = missionService.save(mission);
        return updatedMission;
    }

    @RequestMapping(value = "/mission", method = RequestMethod.DELETE)
    Map<String, String> deleteMission(@RequestParam Integer id){
        Map<String, String> status = new HashMap<>();   // ad hoc JSON creation
        Optional<Mission> mission = missionService.findById(id);
        if(mission.isPresent()) {
            missionService.delete(mission.get());
            status.put("Status", "Mission deleted successfully");
        }
        else {
            status.put("Status", "Mission does not exist");
        }
        return status;
    }
    @RequestMapping(value= "/missions", method = RequestMethod.GET)
    @ResponseBody
    public List<Mission> search(@RequestParam(value="id", required = false) Integer request_id) {
        if (request_id != null) {
            List<Mission> list = new ArrayList<Mission>();
            Optional<Mission> product = missionService.findById(request_id);
            if (product.isPresent()) {
                list.add(product.get());
            }
            return list;
        }
        return missionService.findAll();
    }

    //TODO: explain that this should be done asynchronously

    @RequestMapping(value = "/missions", method = RequestMethod.POST)
    String addAllMissions(@RequestBody List<Mission> missionList){
        missionService.saveAll(missionList);
        return "SUCCESS";
    }

    @RequestMapping(value = "/missions", method = RequestMethod.DELETE)
    String addAllMissions(){
        missionService.deleteAll();
        return "SUCCESS";
    }
}
