Feature: Copy music files to target

  Scenario: Copy music of a playlist to target
    Given I have a playlist defined
    When I execute
    Then the files from the playlist should be copies to target
