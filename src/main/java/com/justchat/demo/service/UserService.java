package com.justchat.demo.service;

import com.justchat.demo.dto.CustomUserDto;
import com.justchat.demo.entity.ChatMessage;
import com.justchat.demo.entity.CustomUser;
import com.justchat.demo.entity.Group;
import com.justchat.demo.entity.MessageStatus;
import com.justchat.demo.repository.ChatMessageRepository;
import com.justchat.demo.repository.CustomUserRepository;
import com.justchat.demo.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserService {


    @Autowired
    ChatMessageRepository chatMessageRepository;


    @Autowired
    CustomUserRepository customUserRepository;

    @Autowired
    GroupRepository groupRepository;


    @Transactional
    public List<CustomUser> getAllUser() {
        return customUserRepository.findAll();
    }


    @Transactional
    public boolean saveUser(CustomUser customUser) {

        if (customUserRepository.existsByLogin(customUser.getLogin())) {
            return false;
        }
        customUserRepository.save(customUser);
        return true;
    }


    @Transactional
    public CustomUser getUserByLogin(String login) {

        return customUserRepository.findByLogin(login);

    }

    @Transactional
    public CustomUser addUser(CustomUser customUser) {

        return customUserRepository.save(customUser);

    }


    @Transactional
    public List<String> getAllNetworks(CustomUser customUser) {


        List<String> networks = new LinkedList<>();

        if (customUser.getFacebook() != null) {
            networks.add(customUser.getFacebook());
        } else networks.add("");

        if (customUser.getTwitter() != null) {
            networks.add(customUser.getTwitter());
        } else networks.add("");

        if (customUser.getInstagram() != null) {
            networks.add(customUser.getInstagram());
        } else networks.add("");

        return networks;

    }


    public String getNetworkUrl(String owner, String nameOfTheNetwork) {

        CustomUser customUser = customUserRepository.findByLogin(owner);
        String url = "";

        if (nameOfTheNetwork.trim().equals("facebook")) {
            url = customUser.getFacebook();
        } else if (nameOfTheNetwork.trim().equals("twitter")) {
            url = customUser.getTwitter();
        } else if (nameOfTheNetwork.trim().equals("instagram")) {
            url = customUser.getInstagram();
        }

        return url;
    }

    @Transactional
    public Set<CustomUser> getSavedUsers(CustomUser currentUser) {

        Set<CustomUser> customUsers = new HashSet<>();
        List<ChatMessage> allMessages = chatMessageRepository.findByMessageStatus(MessageStatus.privateMessage);


        for (ChatMessage allMessage : allMessages) {

            if (getUserByLogin(allMessage.getTo()).equals(currentUser)) {
                customUsers.add(allMessage.getCustomUser());
            }
        }

        List<ChatMessage> messages = chatMessageRepository.findByCustomUser(currentUser);

        customUsers.add(currentUser);

        for (ChatMessage message : messages) {

            customUsers.add(getUserByLogin(message.getTo()));

        }


        return customUsers;

    }

    @Transactional
    public Set<CustomUser> searchUser(String login) {

        return customUserRepository.findByLoginStartsWith(login);


    }

    @Transactional
    public Set<Group> getSavedGroups(CustomUser currentUser) {

        Set<Group> groups = new HashSet<>(currentUser.getGroup());


        return groups;

    }

    @Transactional
    public Set<CustomUserDto> searchUsersToAddingToGroup(String login, String groupName) {

        Set<CustomUserDto> result = new LinkedHashSet<>();
        Group group = groupRepository.findByName(groupName);


        Set<CustomUser> allUsers = customUserRepository.findByLoginStartsWith(login);

        for (CustomUser customUser : allUsers
        ) {
            if ((customUser.getGroup() == null || !customUser.getGroup().contains(group)))
                result.add(new CustomUserDto(customUser.getLogin(), customUser.getAvatar()));

        }


        return result;

    }

    public boolean leaveGroup(String loginCurrentUser, String groupName) {

        CustomUser currentUser = getUserByLogin(loginCurrentUser);
        Group group = groupRepository.findByName(groupName);

        if (currentUser == null || group == null) {
            return false;
        }

        group.getUsers().remove(currentUser);
        currentUser.getGroup().remove(group);
        groupRepository.save(group);
        customUserRepository.save(currentUser);

        return true;
    }


    private String getLoginCurrentUser() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String login = loggedInUser.getName();

        return login;
    }


}
