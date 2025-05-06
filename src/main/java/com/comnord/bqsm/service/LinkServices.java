package com.comnord.bqsm.service;

import com.comnord.bqsm.exception.LinksNotFoundException;
import com.comnord.bqsm.exception.ServiceException;
import com.comnord.bqsm.model.BreveEntity;
import com.comnord.bqsm.model.LinkEntity;
import com.comnord.bqsm.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkServices {

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private BreveServices breveServices;

    public List<LinkEntity> getLinksByBreveId(BreveEntity breveId) {
        try {
            List<LinkEntity> links = linkRepository.findAllLinksByBreveId(breveId);
            if (links.isEmpty()) {
                throw new LinksNotFoundException("No links found for the Breve with ID: " + breveId.getId());
            }
            return links;
        } catch (Exception e) {
            throw new ServiceException("Failed to retrieve links", e);
        }
    }

    public LinkEntity saveLink(LinkEntity link) {
        try {
            return linkRepository.save(link);
        } catch (Exception e) {
            throw new ServiceException("Failed to save link", e);
        }
    }
}
