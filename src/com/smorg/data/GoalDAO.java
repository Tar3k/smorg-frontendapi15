package com.smorg.data;

import java.io.Serializable;
import java.util.ArrayList;

public interface GoalDAO extends Serializable{

    /**
     * Persists Goal information to a persistent data store
     */
    public void addGoal(Goal goal);

    /**
     * Gets all of the Goal instances in the data store
     */
    public ArrayList<Goal> getAllGoals(String userId);

    /**
     * Removed a Goal instance in data store that
     * matches Goal
     */
    public void removeGoal(Long goalId);
}
