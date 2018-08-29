package com.bcfou.controller;

import com.bcfou.entity.Machine;
import com.bcfou.entity.Order;
import com.bcfou.entity.User;
import com.bcfou.repository.MachineRepository;
import com.bcfou.repository.OrderRepository;

import com.bcfou.repository.UserRepository;
import com.bcfou.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.security.auth.Subject;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author: 编程否
 * @Date: 2018/8/26 9:12
 */
@Controller
@Slf4j
public class OrderController {
    @Resource
    private OrderRepository orderRepository;

    @Autowired
    private MachineRepository machineRepository;

    @Autowired
    private UserRepository userRepository;
    /**
     * 订单方法
     * @param: [id, model, ra]
     * @return java.lang.String
     * @author 编程否
     * @date 2018/8/26 23:24
     */
    @RequestMapping("/order/{id}")
    @Transactional
    public String order(@PathVariable("id") int id, Model model, RedirectAttributes ra){
        //1、查询订单，判断是否已经被借走了
        Order order = orderRepository.findByMachineIdAndStatus((long) id, 0);
        Machine machine = machineRepository.getOne((long) id);
        if (order == null){
            //2、判断设备是否存在或者损坏
            if (machine != null){
                if (machine.getMachinesStatus() == 0){
                    model.addAttribute("machine", machine);
                }else {
                    model.addAttribute("msg", "设备已报废或待维修。");
                    return "order";
                }
            }else {
                model.addAttribute("msg", "参数不正确！");
                return "error";
            }

        }else {
            ra.addAttribute("machine", machine);
            ra.addAttribute("order", order);
            //如果订单已存在，跳转到归还页面
            return "redirect:/restore";
        }

        return "order";
    }
    /**
     * 归还视图方法
     * @param: [machine, order, model]
     * @return java.lang.String
     * @author 编程否
     * @date 2018/8/26 23:24
     */
    @GetMapping("/restore")
    @Transactional
    public String restore(Long machine, Long order, Model model){
        Machine machine1 = machineRepository.getOne(machine);
        Order order1 = orderRepository.getOne(order);
        User whoAmI = ShiroUtils.getUser();
        User user = userRepository.getOne(order1.getUId());
        model.addAttribute("machine", machine1);
        model.addAttribute("whoami",whoAmI);
        model.addAttribute("user", user);
        model.addAttribute("order", order1);
        return "restore";
    }
    /**
     * 借用方法
     * @param: [model, id]
     * @return java.lang.String
     * @author 编程否
     * @date 2018/8/26 23:25
     */
    @RequestMapping("/store")
    @Transactional
    public String store(RedirectAttributes model, @RequestParam("machineId") Long id,  Model m){
        Order order = new Order();
        Order orderList = orderRepository.findByMachineIdAndStatus(id, 0);
        if (orderList == null){
            order.setMachineId(id);
            User user = ShiroUtils.getUser();
            //设置用户ID
            order.setUId(user.getUserId());
            //保存数据
            try{
                orderRepository.save(order);
                m.addAttribute("msg", "恭喜，您现在可以将设备从仓库拿出了！");
                return "order";
            }catch (Exception e){
                m.addAttribute("msg", "抱歉，系统似乎出现了一些错误，本次操作失败！");
                return "order";
            }

        }else {
            m.addAttribute("msg", "抱歉，系统似乎出现了一些错误，本次操作失败！");
            return "error";
        }
    }
    /**
     * 归还方法
     * @param: [model, id]
     * @return java.lang.String
     * @author 编程否
     * @date 2018/8/26 23:25
     */
    @PostMapping("/restore")
    @Transactional
    public String restore(Model model, @RequestParam("orderId") int id){
        Order order = orderRepository.getOne((long) id);
        if (order != null){
            //查询用户
            User user = userRepository.getOne(order.getUId());
            order.setStatus(1);
            //获取当前用户
            User whoAmI = ShiroUtils.getUser();
            //判断当前用户是否是借用人，如果不是就覆盖掉借用人。
            if (!user.equals(whoAmI)){
                order.setUId(whoAmI.getUserId());
            }
            orderRepository.save(order);
            model.addAttribute("msg", "归还成功");
            return "restore";
        }else {
            model.addAttribute("msg", "参数有误");
            return "error";
        }
    }
}
