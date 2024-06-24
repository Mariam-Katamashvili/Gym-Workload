package com.mariamkatamashvili.trainerwork.steps;

import com.mariamkatamashvili.trainerwork.dto.ActionType;
import com.mariamkatamashvili.trainerwork.dto.WorkloadDTO;
import com.mariamkatamashvili.trainerwork.entity.Months;
import com.mariamkatamashvili.trainerwork.entity.TrainersWork;
import com.mariamkatamashvili.trainerwork.entity.Years;
import com.mariamkatamashvili.trainerwork.repository.WorkloadRepository;
import com.mariamkatamashvili.trainerwork.security.JwtTokenProvider;
import com.mariamkatamashvili.trainerwork.service.WorkloadService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
@CucumberContextConfiguration
public class WorkloadServiceImplTest {
    @Autowired
    private WorkloadService workloadService;

    @Autowired
    private WorkloadRepository workloadRepository;

    @Before
    public void cleanDatabase() {
        workloadRepository.deleteAll();
    }

    @Given("the database contains a trainer with username {string} and the following yearly summaries:")
    public void givenTheDatabaseContainsATrainerWithUsernameAndTheFollowingYearlySummaries(
            String username,
            DataTable dataTable
    ) {
        TrainersWork trainer = new TrainersWork();
        trainer.setUsername(username);
        trainer.setYears(new ArrayList<>());

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> columns : rows) {
            int year = Integer.parseInt(columns.get("year"));
            Month month = Month.valueOf(columns.get("month"));
            int workAmount = Integer.parseInt(columns.get("workAmount"));

            Years yearlySummary = trainer.getYears().stream()
                    .filter(ys -> ys.getYear() == year)
                    .findFirst()
                    .orElseGet(() -> {
                        Years ys = new Years();
                        ys.setYear(year);
                        ys.setMonths(new ArrayList<>());
                        trainer.getYears().add(ys);
                        return ys;
                    });

            Months monthlySummary = new Months();
            monthlySummary.setMonth(month);
            monthlySummary.setWorkAmount(workAmount);
            yearlySummary.getMonths().add(monthlySummary);
        }

        workloadRepository.save(trainer);
    }

    @Given("the database does not contain a trainer with username {string}")
    public void givenTheDatabaseDoesNotContainATrainerWithUsername(String username) {
        workloadRepository.deleteAll();
    }

    @When("the workload is added with the following details:")
    public void whenTheWorkloadIsAddedWithTheFollowingDetails(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> columns : rows) {
            WorkloadDTO request = WorkloadDTO.builder()
                    .username(columns.get("username"))
                    .date(LocalDate.parse(columns.get("date")))
                    .duration(Integer.parseInt(columns.get("duration")))
                    .actionType(ActionType.valueOf(columns.get("actionType")))
                    .build();

            workloadService.addWorkload(request);
        }
    }

    @When("the workload is deleted with the following details:")
    public void whenTheWorkloadIsDeletedWithTheFollowingDetails(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> columns : rows) {
            WorkloadDTO request = WorkloadDTO.builder()
                    .username(columns.get("username"))
                    .date(LocalDate.parse(columns.get("date")))
                    .duration(Integer.parseInt(columns.get("duration")))
                    .actionType(ActionType.valueOf(columns.get("actionType")))
                    .build();
            workloadService.addWorkload(request);
        }
    }

    @Then("the trainer's monthly summary for JANUARY {int} should be updated to {int}")
    public void thenTheTrainersMonthlySummaryShouldBeUpdated(int year, int expectedWorkAmount) {
        TrainersWork trainer = workloadRepository.findByUsername("john_doe").orElseThrow();
        Years yearlySummary = trainer.getYears().stream()
                .filter(ys -> ys.getYear() == year)
                .findFirst()
                .orElseThrow();

        Months monthlySummary = yearlySummary.getMonths().stream()
                .filter(ms -> ms.getMonth() == Month.JANUARY)
                .findFirst()
                .orElseThrow();

        Assertions.assertEquals(expectedWorkAmount, monthlySummary.getWorkAmount());
    }

    @Then("a new trainer should be created with username {string} and the monthly summary for JANUARY {int} should be {int}")
    public void thenANewTrainerShouldBeCreatedWithUsernameAndTheMonthlySummaryForJANUARYShouldBe(String username, int year, int expectedWorkAmount) {
        TrainersWork trainer = workloadRepository.findByUsername(username).orElseThrow();
        Years yearlySummary = trainer.getYears().stream()
                .filter(ys -> ys.getYear() == year)
                .findFirst()
                .orElseThrow();

        Months monthlySummary = yearlySummary.getMonths().stream()
                .filter(ms -> ms.getMonth() == Month.JANUARY)
                .findFirst()
                .orElseThrow();

        Assertions.assertEquals(expectedWorkAmount, monthlySummary.getWorkAmount());
    }
}