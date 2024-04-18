package com.alejogalizzi.teams.repository;

import com.alejogalizzi.teams.model.entity.Team;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITeamRepository extends JpaRepository<Team, Long> {

  List<Team> findByNombreContaining(String name);

}
